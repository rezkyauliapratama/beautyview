package android.beautyview.basic

import android.beautyview.R
import android.beautyview.utility.BeautyView
import android.content.Context
import android.content.res.ColorStateList
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayout


class TabView : LinearLayout {
    private var newTab: TextView? = null
    private var title: CharSequence? = null
    private var textColors: ColorStateList? = null

    constructor(context: Context) : super(context) {
        init(context, null)

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val layout = this
        layout.orientation = LinearLayout.HORIZONTAL
        layout.gravity = Gravity.CENTER
        newTab = TextView(getContext())
        newTab?.setSupportTextAppearance(R.style.TextAppearance_Design_Tab)
        newTab?.typeface = BeautyView.getBold()
        newTab?.setAllCaps(true)
        newTab?.setGravity(Gravity.CENTER)
        if (textColors != null)
            newTab?.setTextColor(textColors)
        newTab?.setMaxLines(1)
        newTab?.setEllipsize(TextUtils.TruncateAt.END)
        newTab?.setText(title)

        layout.addView(newTab)

    }

    fun setTextColors(textColors: ColorStateList) {
        this.textColors = textColors
        newTab?.setTextColor(textColors)
    }

    fun setTitle(title: CharSequence) {
        this.title = title
        newTab?.text = title
    }

    companion object {

        fun getTabView(tab: TabLayout.Tab): View? {
            return if (tab.customView is TabView)
                tab.customView
            else
                null
        }
    }
}
