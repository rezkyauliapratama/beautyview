package android.beautyview.basic

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager


open class ViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    var isPagingEnabled: Boolean = false

    init {
        this.isPagingEnabled = true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (isPagingEnabled) super.onTouchEvent(event) else false
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (isPagingEnabled) super.onInterceptTouchEvent(event) else false
    }

}
