package android.beautyview.basic

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.beautyview.R
import android.beautyview.utility.BeautyView
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import android.widget.TextView

class TextView : TextView {
    constructor(context: Context) : super(context) {
        setCustomTypeface(context, null)

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setCustomTypeface(context, attrs)

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setCustomTypeface(context, attrs)

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {

        setCustomTypeface(context, attrs)
    }

    @SuppressLint("PrivateResource", "CustomViewStyleable")
    private fun setCustomTypeface(context: Context, attrs: AttributeSet?) {
        if (isInEditMode)
            return
        val a = context.obtainStyledAttributes(attrs, R.styleable.TextAppearance)
        val style = a.getInt(R.styleable.TextAppearance_android_textStyle, Typeface.NORMAL)

        setTextStyle(style)
        a.recycle()

    }

    fun setTextStyle(style: Int) {
        BeautyView.setTextStyle(this, style)
    }

    fun setSupportTextAppearance(resId: Int) {
        if (Build.VERSION.SDK_INT >= 23)
            this.setTextAppearance(resId)
        else
            this.setTextAppearance(context, resId)
    }

}