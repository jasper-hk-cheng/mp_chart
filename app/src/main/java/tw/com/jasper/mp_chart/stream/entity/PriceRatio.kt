package tw.com.jasper.mp_chart.stream.entity

import com.google.gson.annotations.SerializedName

/**
 * Price-to-Earnings Ratio 本益比 PE Ratio
 * Price-to-Book Ratio 本淨比 PB Ratio
 */
data class PriceRatio(
    @SerializedName("股票代號")
    val stockCode: String,
    @SerializedName("股票名稱")
    val stockName: String,
    @SerializedName("本益比基準")
    val peRatioBaseList: List<Float>,
    @SerializedName("本淨比基準")
    val pbRatioBaseList: List<Float>,
    @SerializedName("河流圖資料")
    val streamChartList: List<StreamChart>,
    @SerializedName("目前本益比")
    val currentPeRatio: Float,
    @SerializedName("目前本淨比")
    val currentPbRatio: Float,
    @SerializedName("同業本益比中位數")
    val tradeReRatioMedian: Float,
    @SerializedName("同業本淨比中位數")
    val tradeRbRatioMedian: Float,
    @SerializedName("本益比股價評估")
    val peRatioSpEstimation: String,
    @SerializedName("本淨比股價評估")
    val pbRatioSpEstimation: String,
    @SerializedName("平均本益比")
    val avgPeRatio: Float,
    @SerializedName("平均本淨比")
    val avgPbRatio: Float,
    @SerializedName("本益成長比")
    val peGrowingRatio: Float,
)
