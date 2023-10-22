package tw.com.jasper.mp_chart.stream.utils

object DateUtils {
    fun formatWithSlash(dateSeries: String): String = "${dateSeries.substring(0, 4)}/${dateSeries.substring(4, 6)}"
}