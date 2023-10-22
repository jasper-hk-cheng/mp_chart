package tw.com.jasper.mp_chart.bar

import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import tw.com.jasper.mp_chart.common.JasperActivity
import tw.com.jasper.mp_chart.databinding.ActivityBar02Binding
import tw.com.jasper.mp_chart.utils.ChartDataFactory.getBarEntryListByPower
import tw.com.jasper.mp_chart.utils.Const.Common.FakeDataSource.ENTRY_LIST_COUNT

/**
 * https://ithelp.ithome.com.tw/articles/10203022
 */
class Bar02Activity : JasperActivity<ActivityBar02Binding>() {

    override fun inflateViewBinding(): ActivityBar02Binding = ActivityBar02Binding.inflate(layoutInflater)

    override fun installView(viewBinding: ActivityBar02Binding) {

    }

    override fun initView(viewBinding: ActivityBar02Binding) {
        val barDataSet = BarDataSet(getBarEntryListByPower(2.3f, ENTRY_LIST_COUNT), "bar title").setStyle()
        viewBinding.barChart.run {
            data = BarData(barDataSet)
            axisLeft.setLeftStyle()
            axisRight.setRightStyle()
            xAxis.setStyle()
            setStyle()
            animate()
        }
    }

    private fun BarDataSet.setStyle(): BarDataSet {
        colors = getBarColors()
        return this
    }

    private fun YAxis.setLeftStyle() {
        axisMinimum = 0f
        axisMaximum = 900f
        labelCount = 9
        setDrawTopYLabelEntry(false)
        setDrawGridLines(false)
    }

    private fun YAxis.setRightStyle() {
        setDrawTopYLabelEntry(false)
        setDrawZeroLine(false)
        setDrawGridLines(false)
        setDrawLabels(false)
    }

    private fun XAxis.setStyle() {
        labelCount = ENTRY_LIST_COUNT
        position = XAxis.XAxisPosition.BOTTOM
        setDrawLabels(true)
        setDrawGridLines(false)
    }

    private fun BarChart.setStyle() {
        setDrawValueAboveBar(true)
        description.isEnabled = false
        legend.isEnabled = false
        setScaleEnabled(false)
        animator.animateX(1200, Easing.EasingOption.Linear)
        animator.animateY(2400, Easing.EasingOption.Linear)
    }

    private fun getBarColors(): List<Int> = listOf(
        android.R.color.holo_orange_dark.toColorInt(),
        android.R.color.holo_blue_dark.toColorInt(),
        android.R.color.holo_green_dark.toColorInt(),
        android.R.color.holo_red_dark.toColorInt(),
    )
}