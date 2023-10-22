package tw.com.jasper.mp_chart.stream.decor

import android.graphics.Canvas
import android.graphics.Path
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.renderer.LineChartRenderer
import com.github.mikephil.charting.utils.Transformer
import com.github.mikephil.charting.utils.ViewPortHandler

class MyLineChartRenderer(
    chart: LineDataProvider,
    animator: ChartAnimator,
    viewPortHandler: ViewPortHandler,
) : LineChartRenderer(chart, animator, viewPortHandler) {

    override fun drawLinearFill(canvas: Canvas?, lineDataSet: ILineDataSet, transformer: Transformer, xBounds: XBounds) {
        val path: Path = mGenerateFilledPathBuffer
        val startingIndex = xBounds.min
        val endingIndex = xBounds.min + xBounds.range
        // prepare for draw step do-while loop
        var currentStartIndex = 0
        var currentEndIndex = INDEX_INTERVAL // 無用的assignment 只是讓人看懂而已
        var iterations = 0
        do {
            currentStartIndex = startingIndex + iterations * INDEX_INTERVAL
            currentEndIndex = currentStartIndex + INDEX_INTERVAL
            currentEndIndex = if (currentEndIndex > endingIndex) endingIndex else currentEndIndex
            if (currentStartIndex <= currentEndIndex) {
                generateFilledPath(lineDataSet, currentStartIndex, currentEndIndex, path)
                transformer.pathValueToPixel(path)
                lineDataSet.fillDrawable?.let {
                    drawFilledPath(canvas, path, it)
                } ?: run {
                    drawFilledPath(canvas, path, lineDataSet.fillColor, lineDataSet.fillAlpha)
                }
            }
            iterations++
        } while (currentStartIndex <= currentEndIndex)
    }

    private fun generateFilledPath(lineDataSet: ILineDataSet, currentStartIndex: Int, currentEndIndex: Int, path: Path) {
        val boundaryEntryList: List<Entry> = (lineDataSet.fillFormatter as MyFillFormatter).getBoundaryEntryList()
        // start drawing
        val initEntry: Entry = lineDataSet.getEntryForIndex(currentStartIndex)
        path.run {
            reset()
            moveTo(initEntry.x, boundaryEntryList.first().y)
            lineTo(initEntry.x, initEntry.y)
        }
        for (x in currentStartIndex + 1..currentEndIndex) {
            val currentEntry = lineDataSet.getEntryForIndex(x)
            path.lineTo(currentEntry.x, currentEntry.y)
        }
        for (x in currentEndIndex downTo currentStartIndex + 1) {
            val previousEntry = boundaryEntryList[x]
            path.lineTo(previousEntry.x, previousEntry.y)
        }
        path.close()
    }

    companion object {
        private const val INDEX_INTERVAL = 128
    }
}