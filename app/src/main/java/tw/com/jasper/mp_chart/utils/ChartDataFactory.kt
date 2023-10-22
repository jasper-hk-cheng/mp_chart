package tw.com.jasper.mp_chart.utils

import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import kotlin.math.pow

object ChartDataFactory {

    /*
        entry
     */
    private const val POWER_STACK_COUNT = 4

    fun getEntryListByPower(power: Float, count: Int): List<Entry> = List(count) { index -> Entry(index.toFloat(), index.toFloat().pow(power)) }

    fun getBarEntryListByPower(power: Float, count: Int): List<BarEntry> = List(count) { index -> BarEntry(index.toFloat(), index.toFloat().pow(power)) }

    fun getBarEntryListByPowerStack(power: Float, count: Int): List<BarEntry> = List(count) { index ->
        val yValue = index.toFloat().pow(power)
        val stackValue = yValue / POWER_STACK_COUNT
        BarEntry(index.toFloat(), floatArrayOf(stackValue, stackValue, stackValue, stackValue))
    }

    /*
        label
     */
    fun getLabelListWithPrefixX(size: Int): List<String> = List(size) { i -> "X$i" }
}
