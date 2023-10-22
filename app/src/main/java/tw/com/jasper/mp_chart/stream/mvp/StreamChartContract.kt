package tw.com.jasper.mp_chart.stream.mvp

import tw.com.jasper.mp_chart.stream.entity.StreamChart

interface StreamChartContract {

    interface IStreamChartPresenter {
        fun getPriceRatioRoot(stokeCode: String)
    }

    interface IStreamChartView {
        fun onPeRatioBaseResult(peRatioBaseList: List<Float>)
        fun onXAxisLabelMapResult(xAxisLabelMap: Map<Float, String>)
        fun onStreamChartListResult(streamChartList: List<StreamChart>)
    }
}
