package tw.com.jasper.mp_chart.stream.mvp

import tw.com.jasper.mp_chart.stream.entity.PriceRatio
import tw.com.jasper.mp_chart.stream.entity.PriceRatioRoot
import tw.com.jasper.mp_chart.stream.entity.StreamChart
import tw.com.jasper.mp_chart.stream.utils.DateUtils.formatWithSlash

class StreamChartPresenter(
    private val streamChartView: StreamChartContract.IStreamChartView
) : StreamChartContract.IStreamChartPresenter {

    private val repository = StreamChartRepository()

    override fun getPriceRatioRoot(stokeCode: String) {
        repository.getPriceRatioRoot(stokeCode, object : IStreamChartRepository.LoadPriceRatioRootCallback {
            override fun onPriceRatioRootResult(priceRatioRoot: PriceRatioRoot) {
                val priseRatio: PriceRatio = priceRatioRoot.data.first()
                val streamChartList = priseRatio.streamChartList.sortedBy { it.yearMonth }
                with(streamChartView) {
                    onPeRatioBaseResult(priseRatio.peRatioBaseList)
                    onXAxisLabelMapResult(getXAxisLabelMap(streamChartList))
                    onStreamChartListResult(streamChartList)
                }
            }
        })
    }

    private fun getXAxisLabelMap(streamChartList: List<StreamChart>): Map<Float, String> {
        // returned map
        val labelMap = mutableMapOf<Float, String>()
        // set data
        streamChartList.map { formatWithSlash(it.yearMonth) }.forEachIndexed { index, yearMonth ->
            labelMap[index.toFloat()] = yearMonth
        }
        return labelMap
    }
}