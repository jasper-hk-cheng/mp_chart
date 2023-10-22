package tw.com.jasper.mp_chart.line

import android.graphics.Color
import androidx.annotation.ColorRes
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import tw.com.jasper.mp_chart.common.JasperActivity
import tw.com.jasper.mp_chart.databinding.ActivityLine02Binding
import tw.com.jasper.mp_chart.utils.ChartDataFactory.getEntryListByPower
import tw.com.jasper.mp_chart.utils.Const
import tw.com.jasper.mp_chart.utils.Const.Common.AxisStyle.CHART_OFFSET_0
import tw.com.jasper.mp_chart.utils.Const.Common.AxisStyle.CHART_OFFSET_5
import tw.com.jasper.mp_chart.utils.Const.Common.DashedLineStyle.DASHED_LINE_LENGTH
import tw.com.jasper.mp_chart.utils.Const.Common.DashedLineStyle.DASHED_PHASE
import tw.com.jasper.mp_chart.utils.Const.Common.DashedLineStyle.DASHED_SPACE_LENGTH
import tw.com.jasper.mp_chart.utils.Const.Common.DescStyle.TESTING_DESC
import tw.com.jasper.mp_chart.utils.Const.Common.DescStyle.TESTING_NO_DATA_DESC
import tw.com.jasper.mp_chart.utils.Const.Common.LineStyle.LINE_WIDTH
import tw.com.jasper.mp_chart.utils.Const.Common.TextStyle.TEXT_SIZE
import java.text.DecimalFormat

/**
 * 昕力資訊 Vivian Wu
 * https://www.tpisoftware.com/tpu/articleDetails/1844
 */
class Line02Activity : JasperActivity<ActivityLine02Binding>() {

    override fun inflateViewBinding(): ActivityLine02Binding = ActivityLine02Binding.inflate(layoutInflater)

    override fun installView(viewBinding: ActivityLine02Binding) {

    }

    override fun initView(viewBinding: ActivityLine02Binding) {
        // green line
        val greenEntryList = getEntryListByPower(2f, Const.Common.FakeDataSource.ENTRY_LIST_COUNT)
        val greenLineDataSet = LineDataSet(greenEntryList, "green").setLineStyle(android.R.color.holo_green_dark, android.R.color.holo_green_light)
        val greenLineEndingEntryList = listOf(greenEntryList.last())
        val greenLineDataSetEnding = LineDataSet(greenLineEndingEntryList, "green ending").setEndingPointStyle(android.R.color.holo_green_dark)
        // blue line
        val blueEntryList = getEntryListByPower(2.3f, Const.Common.FakeDataSource.ENTRY_LIST_COUNT)
        val blueLineDataSet = LineDataSet(blueEntryList, "blue").setLineStyle(android.R.color.holo_blue_dark, android.R.color.holo_blue_light)
        val blueLineEndingEntryList = listOf(blueEntryList.last())
        val blueLineDataSetEnding = LineDataSet(blueLineEndingEntryList, "blue ending").setEndingPointStyle(android.R.color.holo_blue_dark)
        // draw
        viewBinding.lineChart.run {
            setYAxisStyle()
            setCommentStyle()
            data = LineData(greenLineDataSet, greenLineDataSetEnding, blueLineDataSet, blueLineDataSetEnding)
            invalidate()
        }
    }

    private fun LineDataSet.setLineStyle(@ColorRes lineColorResId: Int, @ColorRes highlightColorResId: Int): LineDataSet {
        // mode
        mode = LineDataSet.Mode.LINEAR // 類型為折線 STEPPED CUBIC_BEZIER HORIZONTAL_BEZIER TODO 用途是什麼？如何讀圖？
        // line
        color = lineColorResId.toColorInt()
        lineWidth = LINE_WIDTH
        enableDashedLine(DASHED_LINE_LENGTH, DASHED_SPACE_LENGTH, DASHED_PHASE)
        // circle
        setDrawCircles(false)
        circleRadius = 0f
        setCircleColor(android.R.color.transparent.toColorInt())
        // circle hole
        setDrawCircleHole(false)
        circleHoleRadius = 0f
        setCircleColorHole(android.R.color.transparent.toColorInt())
        // y axis value
        setDrawValues(true)
        valueTextSize = TEXT_SIZE
        valueFormatter = MyValueFormat("##.##")
        // line area filling
        setDrawFilled(false)
        // highlight
        isHighlightEnabled = true
        enableDashedHighlightLine(DASHED_LINE_LENGTH, DASHED_SPACE_LENGTH, DASHED_PHASE)
        highlightLineWidth = LINE_WIDTH // TODO:  DisplayMetrics.density 密度的單位是什麼？
        highLightColor = highlightColorResId.toColorInt()
        return this
    }

    private fun LineDataSet.setEndingPointStyle(@ColorRes lineColorResId: Int): LineDataSet {
        // line
        color = lineColorResId.toColorInt()
        // circle
        setDrawCircles(true)
        circleRadius = 3f
        setCircleColor(lineColorResId.toColorInt())
        // circle hole
        setDrawCircleHole(false)
        circleHoleRadius = 0f
        setCircleColorHole(android.R.color.transparent.toColorInt())
        return this
    }

    private fun LineChart.setYAxisStyle() {
        axisRight.isEnabled = false
        axisLeft.setStyle()
    }

    private fun YAxis.setStyle() {
        // label
        labelCount = 8
        textColor = Color.GRAY
        textSize = TEXT_SIZE
        setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        xOffset = CHART_OFFSET_5
        yOffset = CHART_OFFSET_0
        // scale TODO 這裡會不會受到 labelCount 的影響？不會 labelCount 會自己決定結果
        axisMaximum = 1000f
        axisMinimum = 0f
        granularity = 50f
        // y value
        setDrawTopYLabelEntry(true)
        // dashed line
        enableGridDashedLine(DASHED_LINE_LENGTH, DASHED_SPACE_LENGTH, DASHED_PHASE)
        // formatter
        valueFormatter = MyYAxisValueFormatter("###.0")
    }

    private fun LineChart.setCommentStyle() {
        description.setStyle()
        legend.setStyle()
        setDrawStyle()
        setGestureSettings()
    }

    private fun Description.setStyle() {
        isEnabled = true
        text = TESTING_DESC
        textSize = TEXT_SIZE
        textColor = android.R.color.holo_orange_dark.toColorInt()
        setPosition(1000f, 2100f) // TODO 原點在左上角 數字代表pixel（要看手機的螢幕尺寸）
    }

    private fun Legend.setStyle() {
        isEnabled = true
        textSize = TEXT_SIZE
        textColor = android.R.color.black.toColorInt()
    }

    private fun LineChart.setDrawStyle() {
        setDrawBorders(true)
        setBackgroundColor(android.R.color.white.toColorInt())
        // no data case
        setNoDataText(TESTING_NO_DATA_DESC)
        setNoDataTextColor(android.R.color.holo_blue_dark.toColorInt())
    }

    private fun LineChart.setGestureSettings() {
        setScaleEnabled(false)
        setTouchEnabled(true)
        setPinchZoom(false)
    }

    /*
     * formatter
     */
    private class MyYAxisValueFormatter(pattern: String) : IAxisValueFormatter {
        private val decimalFormat = DecimalFormat(pattern)
        override fun getFormattedValue(value: Float, axis: AxisBase?): String = decimalFormat.format(value)
    }

    private class MyValueFormat(pattern: String) : IValueFormatter {
        private val decimalFormat = DecimalFormat(pattern)
        override fun getFormattedValue(value: Float, entry: Entry?, dataSetIndex: Int, viewPortHandler: ViewPortHandler?): String = decimalFormat.format(value)
    }
}
