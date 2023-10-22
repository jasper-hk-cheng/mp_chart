package tw.com.jasper.mp_chart.combined

import android.graphics.Color
import tw.com.jasper.mp_chart.common.JasperActivity
import tw.com.jasper.mp_chart.databinding.ActivityCombined01Binding
import tw.com.jasper.mp_chart.utils.Const.Common.DescStyle.TESTING_NO_DATA_DESC

/**
 * TODO
 *  combined chart
 *  https://blog.csdn.net/ww897532167/article/details/74178449
 */
class Combined01Activity : JasperActivity<ActivityCombined01Binding>() {

    override fun inflateViewBinding(): ActivityCombined01Binding = ActivityCombined01Binding.inflate(layoutInflater)

    override fun installView(viewBinding: ActivityCombined01Binding) {

    }

    override fun initView(viewBinding: ActivityCombined01Binding) {
        viewBinding.combinedChart.run {
            setNoDataText(TESTING_NO_DATA_DESC)
            setNoDataTextColor(Color.RED)
        }
    }
}
