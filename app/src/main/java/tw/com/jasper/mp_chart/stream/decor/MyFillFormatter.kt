package tw.com.jasper.mp_chart.stream.decor

import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class MyFillFormatter(private val boundaryDataSet: ILineDataSet) : IFillFormatter {
    // XXX 這裡被棄用看起來有點怪...
    override fun getFillLinePosition(dataSet: ILineDataSet?, dataProvider: LineDataProvider?): Float = 0f
    fun getBoundaryEntryList(): List<Entry> = (boundaryDataSet as DataSet<*>).values
}
