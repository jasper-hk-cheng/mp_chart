package tw.com.jasper.mp_chart.stream.entity

import com.google.gson.annotations.SerializedName

data class StreamChart(
    @SerializedName("年月")
    val yearMonth: String,
    @SerializedName("月平均收盤價")
    val monthAvgClosingPrice: Float,
    @SerializedName("近四季EPS")
    val latest4QuarterEPS: Float,
    @SerializedName("月近四季本益比")
    val monthLatest4QuarterPeRatio: Float,
    @SerializedName("本益比股價基準")
    val peRatioSpBaseList: List<Float>,
    @SerializedName("近一季BPS")
    val latest1QuarterBPS: Float,
    @SerializedName("月近一季本淨比")
    val monthLatest1QuarterPbRatio: Float,
    @SerializedName("本淨比股價基準")
    val pbRatioSpBaseList: List<Float>,
    @SerializedName("平均本益比")
    val avgPeRatio: Float,
    @SerializedName("平均本淨比")
    val avgPbRatio: Float,
    @SerializedName("近3年年複合成長")
    val latest3yearsCompoundGrowing: Float,
)
