package tw.com.jasper.mp_chart.line

import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import tw.com.jasper.mp_chart.common.JasperActivity
import tw.com.jasper.mp_chart.databinding.ActivityLine01Binding
import tw.com.jasper.mp_chart.utils.ChartDataFactory.getEntryListByPower
import tw.com.jasper.mp_chart.utils.Const.Common.FakeDataSource.ENTRY_LIST_COUNT
import tw.com.jasper.mp_chart.utils.Const.LineChart.CIRCLE_HOLE_RADIUS
import tw.com.jasper.mp_chart.utils.Const.LineChart.CIRCLE_RADIUS

/**
 * http://mahaljsp.asuscomm.com/index.php/2019/11/04/android_linechart/
 */
class Line01Activity : JasperActivity<ActivityLine01Binding>() {

    override fun inflateViewBinding(): ActivityLine01Binding = ActivityLine01Binding.inflate(layoutInflater)

    override fun installView(viewBinding: ActivityLine01Binding) {

    }

    override fun initView(viewBinding: ActivityLine01Binding) {
        val linePow20 = LineDataSet(getEntryListByPower(LineDataSeed.DATA_SET_1.pow, ENTRY_LIST_COUNT), LineDataSeed.DATA_SET_1.label).apply {
            // circles
            setDrawCircles(true)
            circleRadius = CIRCLE_RADIUS
            setCircleColor(android.R.color.holo_orange_dark.toColorInt())
            // circle hole
            setDrawCircleHole(true)
            circleHoleRadius = CIRCLE_HOLE_RADIUS
            setCircleColorHole(android.R.color.holo_orange_light.toColorInt())
        }
        val linePow23 = LineDataSet(getEntryListByPower(LineDataSeed.DATA_SET_2.pow, ENTRY_LIST_COUNT), LineDataSeed.DATA_SET_2.label).apply {
            // circles
            setDrawCircles(true)
            circleRadius = CIRCLE_RADIUS
            setCircleColor(android.R.color.holo_green_dark.toColorInt())
            // circle hole
            setDrawCircleHole(true)
            circleHoleRadius = CIRCLE_HOLE_RADIUS
            setCircleColorHole(android.R.color.holo_green_light.toColorInt())
        }
        viewBinding.lineChart.run {
            data = LineData(listOf(linePow20, linePow23))
            invalidate()
        }
    }

    private enum class LineDataSeed(val label: String, val pow: Float) {
        DATA_SET_1("power 2.0", 2f),
        DATA_SET_2("power 2.3", 2.3f),
    }
}
