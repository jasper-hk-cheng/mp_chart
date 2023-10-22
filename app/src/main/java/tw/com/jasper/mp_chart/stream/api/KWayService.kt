package tw.com.jasper.mp_chart.stream.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import tw.com.jasper.mp_chart.stream.entity.PriceRatioRoot

interface KWayService {
    @GET("/v2/per-river/interview")
    fun getSpRatio(@Query("stock_id") id: String): Call<PriceRatioRoot>
}
