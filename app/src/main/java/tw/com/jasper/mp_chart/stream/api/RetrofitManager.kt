package tw.com.jasper.mp_chart.stream.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager {

    val kWayService: KWayService = getService()

    private fun getService(): KWayService {
        val okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(KWayService::class.java)
    }

    private const val CONNECT_TIMEOUT = 30L
    private const val BASE_URL = "https://api.nstock.tw"
}
