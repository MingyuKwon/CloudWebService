package com.example.cloudwebservice5.Tools

import android.util.Log
import com.example.cloudwebservice5.Data.RecommendationChargeData
import com.example.cloudwebservice5.Data.RecommendationData
import com.example.cloudwebservice5.Data.StoreData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
        private var retrofitRecommend: Retrofit? = null
        private var retrofitAnaly: Retrofit? = null
        private val BASE_URL_Recommen = "https://bj3i65gheg.execute-api.ap-northeast-2.amazonaws.com/"
        private val BASE_URL_Analy = "https://p8hwrsdfgk.execute-api.ap-northeast-2.amazonaws.com/"

        fun getClientAnaly(): Retrofit? {
            if (retrofitAnaly == null) {
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


                retrofitAnaly = Retrofit.Builder()
                    .baseUrl(BASE_URL_Analy)
                    .client(unsafeOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofitAnaly
        }

        fun getClientRecommend(): Retrofit? {
            if (retrofitRecommend == null) {
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


                retrofitRecommend = Retrofit.Builder()
                    .baseUrl(BASE_URL_Recommen)
                    .client(unsafeOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofitRecommend
        }

        suspend fun getRecommendData(region: String?, largeBusiness: String?, mdBusiness: String?, year: String? = "2022"): RecommendationData? {
            val service = getClientRecommend()!!.create(getRecommendation::class.java)

            return try {
                val response = service.getData(region, largeBusiness, mdBusiness, year)
                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.e("getRecommendData Error", response.message())
                    null
                }
            } catch (e: Exception) {
                Log.e("getRecommendData Error", e.message ?: "Unknown error")
                null
            }
        }


        suspend fun getRecommendChargeData(brandName: String?, year: String? = "2022"): RecommendationChargeData? {
            val service = getClientRecommend()!!.create(getRecommendationCharge::class.java)

            return try {
                val response = service.getData(brandName, year)
                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.e("getRecommendChargeData Error", response.message())
                    null
                }
            } catch (e: Exception) {
                Log.e("getRecommendChargeData Error", e.message ?: "Unknown error")
                null
            }

        }

        suspend fun getStoreData( largeBusiness: String?, mdBusiness: String?, smallBusiness: String?, ctprvnNm: String?, signguNm: String?  = "", adongNm: String? = "" ): List<StoreData>? {
            val service = getClientAnaly()!!.create(getStore::class.java)

            return try {
                val response = service.getData(ctprvnNm, signguNm , adongNm, largeBusiness, mdBusiness, smallBusiness)
                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.e("getStoreData Error", response.message())
                    null
                }
            } catch (e: Exception) {
                Log.e("getStoreData Error", e.message ?: "Unknown error")
                null
            }

        }
}

}

interface getRecommendation {
    @GET("franchise/recommendation")
    suspend fun getData(@Query("region") region: String?,
                @Query("largeBusiness") largeBusiness: String?,
                @Query("mdBusiness") mdBusiness: String?,
                @Query("year") year: String? ): Response<RecommendationData>
}

interface getRecommendationCharge {
    @GET("franchise/brand-charges")
    suspend fun getData(@Query("brandName") brandName: String?,
                @Query("year") year: String? ): Response<RecommendationChargeData>
}

interface getStore {
    @GET("smallBusiness/analysis")
    suspend fun getData(@Query("ctprvnNm") ctprvnNm: String?,
                        @Query("signguNm") signguNm: String?,
                        @Query("adongNm") adongNm: String?,
                        @Query("largeBusiness") largeBusiness: String?,
                        @Query("mdBusiness") mdBusiness: String?,
                        @Query("smallBusiness") smallBusiness: String? ): Response<List<StoreData>>
}