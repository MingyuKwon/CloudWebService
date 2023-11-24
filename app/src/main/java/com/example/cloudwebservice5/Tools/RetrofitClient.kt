package com.example.cloudwebservice5.Tools

import android.util.Log
import com.example.cloudwebservice5.Data.RecommendationData
import com.google.android.play.integrity.internal.t
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class RetrofitClient {

    companion object {
        private var retrofit: Retrofit? = null
        private val BASE_URL = "https://bj3i65gheg.execute-api.ap-northeast-2.amazonaws.com/"

        fun getClient(): Retrofit? {
            if (retrofit == null) {
                // Retrofit 인스턴스 생성
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY) // 로그 레벨 설정

                val unsafeOkHttpClient = OkHttpClient.Builder().apply {
                    // Create a trust manager that does not validate certificate chains
                    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
                        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
                        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                    })

                    // Install the all-trusting trust manager
                    val sslContext = SSLContext.getInstance("SSL").apply {
                        init(null, trustAllCerts, java.security.SecureRandom())
                    }
                    sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)

                    // Don't check Hostnames, either.
                    // CAUTION: This makes the connection vulnerable to MITM attacks!
                    hostnameVerifier { _, _ -> true }
                    addInterceptor(logging)
                }.build()


                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(unsafeOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

        fun getRecommendData(region: String?, largeBusiness: String?, mdBusiness: String?, year: String? = "2022"): RecommendationData? {

            var responseBody : RecommendationData? = null
            val service = RetrofitClient.getClient()!!.create(getRecommendation::class.java)

            service.getData(region, largeBusiness, mdBusiness, year)!!.enqueue(object : Callback<RecommendationData?>{
                override fun onResponse(
                    call: Call<RecommendationData?>,
                    response: Response<RecommendationData?>
                ) {
                    responseBody = response.body() as RecommendationData
                    Log.e("getRecommendData Error", responseBody!!.SaleData!!.count().toString())
                    Log.e("getRecommendData Error", responseBody!!.GrowthData!!.count().toString())
                    Log.e("getRecommendData Error", responseBody!!.CountData!!.count().toString())

                }

                override fun onFailure(call: Call<RecommendationData?>, t: Throwable) {
                    Log.e("getRecommendData Error", t.message.toString())
                }

            }
        )

            return responseBody

        }
}

}

interface getRecommendation {
    @GET("franchise/recommendation")
    fun getData(@Query("region") region: String?,
                @Query("largeBusiness") largeBusiness: String?,
                @Query("mdBusiness") mdBusiness: String?,
                @Query("year") year: String? ): Call<RecommendationData>
}
