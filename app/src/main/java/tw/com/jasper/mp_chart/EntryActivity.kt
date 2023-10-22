package tw.com.jasper.mp_chart

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tw.com.jasper.mp_chart.bar.Bar01Activity
import tw.com.jasper.mp_chart.bar.Bar02Activity
import tw.com.jasper.mp_chart.combined.Combined01Activity
import tw.com.jasper.mp_chart.common.JasperActivity
import tw.com.jasper.mp_chart.databinding.ActivityEntryBinding
import tw.com.jasper.mp_chart.databinding.ItemEntryBinding
import tw.com.jasper.mp_chart.line.Line01Activity
import tw.com.jasper.mp_chart.line.Line02Activity
import tw.com.jasper.mp_chart.stream.Stream01Activity

class EntryActivity : JasperActivity<ActivityEntryBinding>() {

    companion object {
        private val ACTIVITY_CLAZZ_LIST = listOf(
            Line01Activity::class.java,
            Line02Activity::class.java,
            Stream01Activity::class.java,
            Combined01Activity::class.java,
            Bar01Activity::class.java,
            Bar02Activity::class.java,
        )
    }

    override fun inflateViewBinding(): ActivityEntryBinding = ActivityEntryBinding.inflate(layoutInflater)

    override fun installView(viewBinding: ActivityEntryBinding) {

    }

    override fun initView(viewBinding: ActivityEntryBinding) {
        viewBinding.rvEntry.run {
            addItemDecoration(DividerItemDecoration(this@EntryActivity, LinearLayoutManager.VERTICAL).apply {
                setDrawable(ColorDrawable(android.R.color.holo_purple.toColorInt()))
            })
            adapter = EntryAdapter(ACTIVITY_CLAZZ_LIST)
        }
    }
}

class EntryAdapter(private val activityClazzList: List<Class<*>>) : RecyclerView.Adapter<EntryAdapter.ViewHolder>() {

    private val entryNameList: List<String> by lazy {
        val activityTextLength = Activity::class.java.simpleName.length
        activityClazzList.map { it.simpleName.substring(0, it.simpleName.length - activityTextLength) }
    }

    class ViewHolder(private val itemViewBinding: ItemEntryBinding) : RecyclerView.ViewHolder(itemViewBinding.root) {
        private var targetClazz: Class<*>? = null

        init {
            itemView.setOnClickListener { itemView ->
                targetClazz?.let { targetClass ->
                    itemView.context.startActivity(Intent(itemView.context, targetClass))
                }
            }
        }

        fun setHolder(entryName: String, targetClazz: Class<*>) {
            itemViewBinding.tvEntryItem.text = entryName
            this.targetClazz = targetClazz
        }
    }

    override fun getItemCount(): Int = activityClazzList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemViewBinding = ItemEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setHolder(entryNameList[position], activityClazzList[position])
    }
}