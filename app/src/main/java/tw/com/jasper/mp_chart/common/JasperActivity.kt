package tw.com.jasper.mp_chart.common

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.viewbinding.ViewBinding

abstract class JasperActivity<T : ViewBinding> : AppCompatActivity(), ActivityInitContract<T> {

    private lateinit var viewBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = inflateViewBinding().apply {
            setContentView(root)
        }
        initSOP()
    }

    override fun getViewBinding(): T = viewBinding

    protected fun @receiver:ColorRes Int.toColorInt(): Int = ResourcesCompat.getColor(resources, this, null)
    protected fun @receiver:StringRes Int.toResString(): String = getString(this)
    protected fun @receiver:DrawableRes Int.toResDrawable(): Drawable = ResourcesCompat.getDrawable(resources, this, null)!!
}

interface ActivityInitContract<T : ViewBinding> {

    fun initSOP() {
        installView(getViewBinding())
        initView(getViewBinding())
    }

    fun inflateViewBinding(): T
    fun getViewBinding(): T

    fun installView(viewBinding: T)
    fun initView(viewBinding: T)
}