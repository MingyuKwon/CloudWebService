import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Environment
import android.util.Base64
import android.util.Log
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
import retrofit2.http.Query
import java.io.File
import java.io.FileOutputStream
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

    public override fun doInBackground(vararg params: Void?): Void? {
        runBlocking {
            try {
                val retrofit = createUnsafeRetrofit(BASE_URL_DOWNLOAD)
                val service = retrofit.create(DownloadService::class.java)

                val response = service.downloadFile(fileName)
                if (response.isSuccessful) {
                    val base64Data = response.body()?.string()
                    if (!base64Data.isNullOrBlank()) {
                        val decodedData: ByteArray = Base64.decode(base64Data, Base64.DEFAULT)

                        // Save the decoded data to a file in external storage
                        val fileName = "downloadedFile.csv"
                        val file = saveFileToExternalStorage(decodedData, fileName)

                        // Open the file using FileProvider
//                        openFileWithFileProvider(file, context)
                    } else {
                        Log.d("DownloadFileTask", "Base64Data is Null")
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

    private suspend fun saveFileToExternalStorage(data: ByteArray, fileName: String): File =
        withContext(Dispatchers.IO) {
            val externalStorageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            val file = File(externalStorageDir, fileName)

            try {
                val fileOutputStream = FileOutputStream(file)
                fileOutputStream.write(data)
                fileOutputStream.close()
            } catch (e: Exception) {
                Log.e("saveFileToExternalStorage Error", e.message ?: "Unknown error")
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
        intent.setDataAndType(uri, "application/octet-stream")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(intent)
    }
}

interface DownloadService {
    @GET("s3/download")
    suspend fun downloadFile(@Query("filename") filename: String): Response<ResponseBody>
}
