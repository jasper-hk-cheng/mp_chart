package tw.com.jasper.mp_chart.stream

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import tw.com.jasper.mp_chart.R
import tw.com.jasper.mp_chart.databinding.ActivityStream01Binding
import tw.com.jasper.mp_chart.stream.decor.MyFillFormatter
import tw.com.jasper.mp_chart.stream.decor.MyLineChartRenderer
import tw.com.jasper.mp_chart.stream.entity.StreamChart
import tw.com.jasper.mp_chart.stream.mvp.StreamChartContract
import tw.com.jasper.mp_chart.stream.mvp.StreamChartPresenter
import tw.com.jasper.mp_chart.stream.utils.DateUtils.formatWithSlash

class Stream01Activity : AppCompatActivity(), StreamChartContract.IStreamChartView {

    private lateinit var viewBinding: ActivityStream01Binding

    private val presenter = StreamChartPresenter(this)

    private lateinit var peRatioBaseList: List<Float>
    private lateinit var xAxisLabelMap: Map<Float, String>
    private lateinit var yearMonthList: List<String>
    private lateinit var streamLineDataSet1x: LineDataSet
    private lateinit var streamLineDataSet2x: LineDataSet
    private lateinit var streamLineDataSet3x: LineDataSet
    private lateinit var streamLineDataSet4x: LineDataSet
    private lateinit var streamLineDataSet5x: LineDataSet
    private lateinit var streamLineDataSet6x: LineDataSet
    private lateinit var monthAvgClosingPriceLineDataSet: LineDataSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityStream01Binding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        initView()
        getStockData()
    }

    private fun initView() {
        with(viewBinding) {
            includeLegendForDate.tvMultiplyIcon.isVisible = false
            includeLegendForStockPrice.tvMultiplyIcon.setBackgroundColor(getColorInt(R.color.stock_price))
            includeLegendForPeRatio6x.tvMultiplyIcon.setBackgroundColor(getColorInt(R.color.pe_ratio_stream_6x))
            includeLegendForPeRatio5x.tvMultiplyIcon.setBackgroundColor(getColorInt(R.color.pe_ratio_stream_5x))
            includeLegendForPeRatio4x.tvMultiplyIcon.setBackgroundColor(getColorInt(R.color.pe_ratio_stream_4x))
            includeLegendForPeRatio3x.tvMultiplyIcon.setBackgroundColor(getColorInt(R.color.pe_ratio_stream_3x))
            includeLegendForPeRatio2x.tvMultiplyIcon.setBackgroundColor(getColorInt(R.color.pe_ratio_stream_2x))
            includeLegendForPeRatio1x.tvMultiplyIcon.setBackgroundColor(getColorInt(R.color.pe_ratio_stream_1x))

            streamChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onValueSelected(entry: Entry?, highlight: Highlight?) {
                    if (entry == null) return
                    val intEntryX = entry.x.toInt()
                    with(viewBinding) {
                        includeLegendForDate.tvMultiplyDesc.text = yearMonthList[intEntryX]
                        val stockPriceTemp = resources.getString(R.string.legend_stock_price)
                        includeLegendForStockPrice.tvMultiplyDesc.text = String.format(stockPriceTemp, monthAvgClosingPriceLineDataSet.values[intEntryX].y)
                        val peRatioTemp = resources.getString(R.string.legend_pe_ratio)
                        includeLegendForPeRatio6x.tvMultiplyDesc.text = String.format(peRatioTemp, peRatioBaseList[5], streamLineDataSet6x.values[intEntryX].y)
                        includeLegendForPeRatio5x.tvMultiplyDesc.text = String.format(peRatioTemp, peRatioBaseList[4], streamLineDataSet5x.values[intEntryX].y)
                        includeLegendForPeRatio4x.tvMultiplyDesc.text = String.format(peRatioTemp, peRatioBaseList[3], streamLineDataSet4x.values[intEntryX].y)
                        includeLegendForPeRatio3x.tvMultiplyDesc.text = String.format(peRatioTemp, peRatioBaseList[2], streamLineDataSet3x.values[intEntryX].y)
                        includeLegendForPeRatio2x.tvMultiplyDesc.text = String.format(peRatioTemp, peRatioBaseList[1], streamLineDataSet2x.values[intEntryX].y)
                        includeLegendForPeRatio1x.tvMultiplyDesc.text = String.format(peRatioTemp, peRatioBaseList[0], streamLineDataSet1x.values[intEntryX].y)
                    }
                }

                override fun onNothingSelected() {}
            })
        }
    }

    private fun getStockData() {
        presenter.getPriceRatioRoot("2330")
    }

    override fun onPeRatioBaseResult(peRatioBaseList: List<Float>) {
        this.peRatioBaseList = peRatioBaseList
    }

    override fun onXAxisLabelMapResult(xAxisLabelMap: Map<Float, String>) {
        this.xAxisLabelMap = xAxisLabelMap
    }

    override fun onStreamChartListResult(streamChartList: List<StreamChart>) {
        initData(streamChartList)
        drawStreamChart()
    }

    private fun drawStreamChart() {
        val lineData = LineData(
            streamLineDataSet6x,
            streamLineDataSet5x,
            streamLineDataSet4x,
            streamLineDataSet3x,
            streamLineDataSet2x,
            streamLineDataSet1x,
            monthAvgClosingPriceLineDataSet,
        )
        viewBinding.streamChart.run {
            data = lineData
            setStreamChartStyle()
            invalidate()
        }
        // 讓legend一開始就有值
        viewBinding.streamChart.highlightValue(0f, 0)
    }

    private fun initData(streamChartList: List<StreamChart>) {
        yearMonthList = streamChartList.map { formatWithSlash(it.yearMonth) }
        val peRatioSpBaseListList: List<List<Float>> = streamChartList.map { it.peRatioSpBaseList }
        streamLineDataSet1x = getStreamLineDataSet(peRatioSpBaseListList, 0, R.color.pe_ratio_stream_1x, null)
        streamLineDataSet2x = getStreamLineDataSet(peRatioSpBaseListList, 1, R.color.pe_ratio_stream_2x, streamLineDataSet1x)
        streamLineDataSet3x = getStreamLineDataSet(peRatioSpBaseListList, 2, R.color.pe_ratio_stream_3x, streamLineDataSet2x)
        streamLineDataSet4x = getStreamLineDataSet(peRatioSpBaseListList, 3, R.color.pe_ratio_stream_4x, streamLineDataSet3x)
        streamLineDataSet5x = getStreamLineDataSet(peRatioSpBaseListList, 4, R.color.pe_ratio_stream_5x, streamLineDataSet4x)
        streamLineDataSet6x = getStreamLineDataSet(peRatioSpBaseListList, 5, R.color.pe_ratio_stream_6x, streamLineDataSet5x)
        monthAvgClosingPriceLineDataSet = getMonthAvgClosingPriceLineDataSet(streamChartList)
    }

    private fun getMonthAvgClosingPriceLineDataSet(streamChartList: List<StreamChart>): LineDataSet {
        val entryList = mutableListOf<Entry>().apply {
            streamChartList.map { it.monthAvgClosingPrice }.forEachIndexed { index, monthAvgClosingPrice ->
                add(Entry(index.toFloat(), monthAvgClosingPrice))
            }
        }
        return LineDataSet(entryList, "").setMonthAvgClosingPriceLineDataSetStyle()
    }

    private fun getStreamLineDataSet(peRatioSpBaseListList: List<List<Float>>, multiplyIndex: Int, @ColorRes lineColorResId: Int, boundaryLineDataSet: ILineDataSet?): LineDataSet {
        val entryList = mutableListOf<Entry>().apply {
            peRatioSpBaseListList.map { it[multiplyIndex] }.forEachIndexed { index, peRatioSpBase ->
                add(Entry(index.toFloat(), peRatioSpBase))
            }
        }
        return LineDataSet(entryList, "").setStreamLineDataSetStyle(getColorInt(lineColorResId), boundaryLineDataSet)
    }

    private fun LineDataSet.setStreamLineDataSetStyle(@ColorInt lineColorInt: Int, boundaryLineDataSet: ILineDataSet?): LineDataSet {
        // shape
        lineWidth = 2f
        // color
        color = lineColorInt
        fillAlpha = 100
        // fill
        setDrawFilled(boundaryLineDataSet != null)
        boundaryLineDataSet?.let {
            fillDrawable = ColorDrawable(lineColorInt)
            fillFormatter = MyFillFormatter(it)
        }
        // circle and hole
        setDrawCircles(false)
        setDrawCircleHole(false)
        return this
    }

    private fun LineDataSet.setMonthAvgClosingPriceLineDataSetStyle(): LineDataSet {
        // shape
        lineWidth = 3f
        // color
        color = getColorInt(R.color.stock_price)
        // circle and hole
        setDrawCircles(false)
        setDrawCircleHole(false)
        return this
    }

    private fun LineChart.setStreamChartStyle() {
        setBackgroundColor(BACKGROUND_COLOR)
        legend.isEnabled = false
        axisLeft.isEnabled = false
        axisRight.run {
            textColor = getColorInt(R.color.text_all)
        }
        xAxis.run {
            textColor = getColorInt(R.color.text_all)
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = IAxisValueFormatter { value, axis ->
                xAxisLabelMap[value]
            }
        }
        renderer = MyLineChartRenderer(this, animator, viewPortHandler)
    }

    private fun getColorInt(@ColorRes colorResId: Int): Int = resources.getColor(colorResId, null)

    companion object {
        private val BACKGROUND_COLOR = Color.BLACK
    }
}
