package tw.com.jasper.mp_chart.stream.mvp

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tw.com.jasper.mp_chart.stream.api.RetrofitManager
import tw.com.jasper.mp_chart.stream.entity.PriceRatioRoot

class StreamChartRepository : IStreamChartRepository {

    override fun getPriceRatioRoot(stockCode: String, callback: IStreamChartRepository.LoadPriceRatioRootCallback) {
        RetrofitManager.kWayService.getSpRatio(stockCode).enqueue(object : Callback<PriceRatioRoot> {
            override fun onResponse(call: Call<PriceRatioRoot>?, response: Response<PriceRatioRoot>?) {
                response?.let {
                    callback.onPriceRatioRootResult(it.body())
                } ?: run {
                    Log.e(LOG_TAG, "response is null !!")
                }
            }

            override fun onFailure(call: Call<PriceRatioRoot>?, t: Throwable?) {
                Log.e(LOG_TAG, Log.getStackTraceString(t))
            }
        })
    }

    companion object {
        private val LOG_TAG = StreamChartRepository::class.java.simpleName
    }
}

interface IStreamChartRepository {
    fun getPriceRatioRoot(stockCode: String, callback: LoadPriceRatioRootCallback)

    interface LoadPriceRatioRootCallback {
        fun onPriceRatioRootResult(priceRatioRoot: PriceRatioRoot)
    }
}
