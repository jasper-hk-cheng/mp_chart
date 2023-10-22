package tw.com.jasper.mp_chart.bar

import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import tw.com.jasper.mp_chart.common.JasperActivity
import tw.com.jasper.mp_chart.databinding.ActivityBar01Binding
import tw.com.jasper.mp_chart.utils.ChartDataFactory.getBarEntryListByPowerStack
import tw.com.jasper.mp_chart.utils.ChartDataFactory.getLabelListWithPrefixX
import tw.com.jasper.mp_chart.utils.Const.Common.FakeDataSource.ENTRY_LIST_COUNT

/**
 * https://bng86.gitbooks.io/android-third-party-/content/mpandroidchart.html
 */
class Bar01Activity : JasperActivity<ActivityBar01Binding>() {

    private lateinit var labelList: List<String>

    override fun inflateViewBinding(): ActivityBar01Binding = ActivityBar01Binding.inflate(layoutInflater)

    override fun installView(viewBinding: ActivityBar01Binding) {

    }

    override fun initView(viewBinding: ActivityBar01Binding) {
        labelList = getLabelListWithPrefixX(ENTRY_LIST_COUNT)
        // bar chart data
        val barDataSet = BarDataSet(getBarEntryListByPowerStack(2.3f, ENTRY_LIST_COUNT), "power 2.3").setStyle()
        val barData = BarData(barDataSet)
        viewBinding.barChart.run {
            data = barData
            setStyle()
            invalidate()
        }
    }

    private fun BarDataSet.setStyle(): BarDataSet {
        setDrawValues(false)
        colors = getStackColors()
        stackLabels = this@Bar01Activity.getStackLabels()
        return this
    }

    private fun BarChart.setStyle() {
        xAxis.run {
            setDrawGridLines(false)
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = IAxisValueFormatter { value, axisBase ->
                labelList[value.toInt()]
            }
        }
        axisRight.isEnabled = false
        axisLeft.setDrawGridLines(true)
        description.isEnabled = false
    }

    private fun getStackLabels(): Array<String> = arrayOf("1st", "2nd", "3rd", "4th")

    private fun getStackColors(): List<Int> = listOf(
        android.R.color.holo_orange_dark.toColorInt(),
        android.R.color.holo_blue_dark.toColorInt(),
        android.R.color.holo_green_dark.toColorInt(),
        android.R.color.holo_red_dark.toColorInt(),
    )
}