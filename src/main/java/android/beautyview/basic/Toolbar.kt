package android.beautyview.basic

import android.content.Context
import android.beautyview.utility.BeautyView
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.widget.Toolbar


class Toolbar : Toolbar {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, @Nullable attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, @Nullable attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (child is TextView) {
            child.typeface = BeautyView.getRegular()
        }
        super.addView(child, index, params)


    }
}
