import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.io.*
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class DownloadFileTask(private val context: Context, private val fileName: String) : AsyncTask<Void, Void, Void>() {

    private val BASE_URL_DOWNLOAD = "https://fgyw5cdf02.execute-api.ap-northeast-2.amazonaws.com/"
    // SSL 트러스트 매니저 배열 정의
    private val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    })

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public override fun doInBackground(vararg params: Void?): Void? {
        runBlocking {
            try {
                val retrofit = createUnsafeRetrofit(BASE_URL_DOWNLOAD)
                val service = retrofit.create(DownloadService::class.java)

                val response = service.downloadFile(fileName)
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        // Save the response directly to a file in external storage
                        val file = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            saveFileToExternalStorage(responseBody.byteStream(), fileName)
                        } else {
                            TODO("VERSION.SDK_INT < TIRAMISU")
                        }

                        // Open the file using FileProvider
                        openFileWithFileProvider(file, context)
                    } else {
                        Log.d("DownloadFileTask", "Response body is null")
                    }
                } else {
                    Log.e("DownloadFileTask Error", response.message())
                }
            } catch (e: Exception) {
                Log.e("DownloadFileTask Error", e.message ?: "Unknown error")
            }
        }

        return null
    }

    private suspend fun saveFileToExternalStorage(inputStream: InputStream, fileName: String): File =
        withContext(Dispatchers.IO) {
            val externalStorageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            val file = File(externalStorageDir, fileName)

            try {
                val fileWriter = BufferedWriter(OutputStreamWriter(FileOutputStream(file), Charsets.UTF_8))
                val bufferedReader = BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8))

                bufferedReader.use { reader ->
                    fileWriter.use { writer ->
                        var line: String?
                        while (reader.readLine().also { line = it } != null) {
                            writer.write(line)
                            writer.newLine()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("saveFileToExternalStorage Error", e.message ?: "알 수 없는 오류")
            }

            file
        }

    private fun openFileWithFileProvider(file: File, context: Context) {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "text/csv")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        val title = "열기할 앱을 선택하세요"
        val chooser = Intent.createChooser(intent, title)

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(chooser)
        } else {
            Toast.makeText(context, "파일을 열 수 있는 앱이 설치되어 있지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createUnsafeRetrofit(baseUrl: String): Retrofit {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        })

        val sslContext = SSLContext.getInstance("SSL").apply {
            init(null, trustAllCerts, java.security.SecureRandom())
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(unsafeOkHttpClient(sslContext))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun unsafeOkHttpClient(sslContext: SSLContext) = OkHttpClient.Builder().apply {
        sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
        hostnameVerifier { _, _ -> true }
    }.build()

//    private suspend fun saveFileToExternalStorage(data: ByteArray, fileName: String): File =
//        withContext(Dispatchers.IO) {
//            val externalStorageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
//            val file = File(externalStorageDir, fileName)
//
//            try {
//                val fileOutputStream = FileOutputStream(file)
//                fileOutputStream.write(data)
//                fileOutputStream.close()
//            } catch (e: Exception) {
//                Log.e("saveFileToExternalStorage Error", e.message ?: "Unknown error")
//            }
//
//            file
//        }
//
//    private fun openFileWithFileProvider(file: File, context: Context) {
//        val uri = FileProvider.getUriForFile(
//            context,
//            "${context.packageName}.provider",
//            file
//        )
//
//        val intent = Intent(Intent.ACTION_VIEW)
//        intent.setDataAndType(uri, "application/octet-stream")
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        context.startActivity(intent)
//    }
}

interface DownloadService {
    @GET("s3/download")
    @Headers("Content-Type: text/csv; charset=utf-8")
    suspend fun downloadFile(@Query("filename") filename: String): Response<ResponseBody>
}
