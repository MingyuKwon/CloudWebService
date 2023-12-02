package com.example.cloudwebservice5.Tools

import android.util.Log
import com.example.cloudwebservice5.Data.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
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
        private val BASE_URL_CONNECT_RDS = "https://fzamwlgtkd.execute-api.ap-northeast-2.amazonaws.com/2023-11-13/"

        val logging = HttpLoggingInterceptor()

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

        fun getClientAnaly(): Retrofit? {
            if (retrofitAnaly == null) {
                // Retrofit 인스턴스 생성
                logging.setLevel(HttpLoggingInterceptor.Level.BODY) // 로그 레벨 설정

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
                logging.setLevel(HttpLoggingInterceptor.Level.BODY) // 로그 레벨 설정

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

        suspend fun loginUser(userId: String, password: String): AuthResponse? {

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_CONNECT_RDS)
                .client(unsafeOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val authService = retrofit.create(AuthService::class.java)

            return try {

                val response = authService.login(userId, password)
                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.e("loginUser Error", response.message())
                    null
                }
            } catch (e: Exception) {
                Log.e("loginUser Error", e.message ?: "Unknown error")
                null
            }
        }

        suspend fun signUpUser(userId: String, password: String, name: String, phoneNumber: String, isCeo: Int, career: Int?): AuthResponse? {

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_CONNECT_RDS)
                .client(unsafeOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val authService = retrofit.create(AuthService::class.java)

            return try {

                val response = authService.signup(userId, password, name, phoneNumber, isCeo, career)
                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.e("signup Error", response.message())
                    null
                }
            } catch (e: Exception) {
                Log.e("signup Error", e.message ?: "Unknown error")
                null
            }
        }

        suspend fun signUpStore(name: String, description: String, address: String, annual_revenue: Int, userId: String): AuthResponse? {

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_CONNECT_RDS)
                .client(unsafeOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val authService = retrofit.create(AuthService::class.java)

            return try {

                val response = authService.signupStore(name, description, address, annual_revenue, userId)
                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.e("signUpStore Error", response.message())
                    null
                }
            } catch (e: Exception) {
                Log.e("signUpStore Error", e.message ?: "Unknown error")
                null
            }
        }

        suspend fun getUsers(userId: String): List<CeoData> {

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_CONNECT_RDS)
                .client(unsafeOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val authService = retrofit.create(AuthService::class.java)

            return try {

                val response = authService.getUsers(userId)
                if (response.isSuccessful) {
                    response.body() ?: emptyList()
                } else {
                    Log.e("getUsers Error", response.message())
                    emptyList()
                }
            } catch (e: Exception) {
                Log.e("getUsers Error", e.message ?: "Unknown error")
                emptyList()
            }
        }

        suspend fun getCeoStore(userId: String): CeoStoreData? {

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_CONNECT_RDS)
                .client(unsafeOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val authService = retrofit.create(AuthService::class.java)

            return try {

                val response = authService.getCeoStore(userId)
                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.e("getCeoStore Error", response.message())
                    null
                }
            } catch (e: Exception) {
                Log.e("getCeoStore Error", e.message ?: "Unknown error")
                null
            }
        }

        suspend fun sendMessage(title: String, content: String, senderId: String, receiverId: String): AuthResponse? {

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_CONNECT_RDS)
                .client(unsafeOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val authService = retrofit.create(AuthService::class.java)

            return try {

                val response = authService.sendMessage(title, content, senderId, receiverId)
                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.e("sendMessage Error", response.message())
                    null
                }
            } catch (e: Exception) {
                Log.e("sendMessage Error", e.message ?: "Unknown error")
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

interface AuthService {
    @POST("auth/login")
    suspend fun login(
        @Query("user_id") userId: String,
        @Query("password") password: String
    ): Response<AuthResponse>

    @POST("auth/signup")
    suspend fun signup(
        @Query("user_id") userId: String,
        @Query("password") password: String,
        @Query("name") name: String,
        @Query("phone_number") phoneNumber: String,
        @Query("is_ceo") isCeo: Int,
        @Query("career") career: Int?
    ): Response<AuthResponse>

    @POST("auth/signup/store")
    suspend fun signupStore(
        @Query("name") name: String,
        @Query("description") description: String,
        @Query("address") address: String,
        @Query("annual_revenue") annualRevenue: Int,
        @Query("user_id") userId: String
    ): Response<AuthResponse>

    @GET("user")
    suspend fun getUsers(
        @Query("user_id") userId: String
    ): Response<List<CeoData>>

    @GET("user/store")
    suspend fun getCeoStore(
        @Query("user_id") userId: String
    ): Response<CeoStoreData>

    @POST("user/message")
    suspend fun sendMessage(
        @Query("title") title: String,
        @Query("content") content: String,
        @Query("sender_id") senderId: String,
        @Query("receiver_id") receiverId: String
    ): Response<AuthResponse>
}
