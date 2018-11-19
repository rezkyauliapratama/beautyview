package android.beautyview.basic

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.NonNull
import com.google.android.material.tabs.TabLayout


class TabLayout : TabLayout {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun addTab(@NonNull tab: Tab, position: Int, setSelected: Boolean) {
        changeFont(tab)
        super.addTab(tab, position, setSelected)
    }

    private fun changeFont(tab: Tab) {
        if (tab.text != null) {
            val layout = TabView(getContext())
            tab.text?.let { layout.setTitle(it) }
            tabTextColors?.let { layout.setTextColors(it) }

            tab.customView = layout
        }
    }

}
