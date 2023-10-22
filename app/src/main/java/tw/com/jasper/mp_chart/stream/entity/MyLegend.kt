package tw.com.jasper.mp_chart.stream.entity

import androidx.annotation.ColorInt

data class MyLegend(
    val multiply: Float,
    val stockPrice: Float,
    @ColorInt val iconColorInt: Int,
)
