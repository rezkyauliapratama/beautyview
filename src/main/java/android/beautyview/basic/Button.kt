package android.beautyview.basic

import android.annotation.SuppressLint
import android.beautyview.R
import android.beautyview.utility.BeautyView
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class Button : AppCompatButton {
    constructor(context: Context) : super(context) {
        setCustomTypeface(context, null)

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setCustomTypeface(context, attrs)

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setCustomTypeface(context, attrs)

    }


    @SuppressLint("CustomViewStyleable", "PrivateResource")
    private fun setCustomTypeface(context: Context, attrs: AttributeSet?) {
        if (isInEditMode)
            return
        val a = context.obtainStyledAttributes(attrs, R.styleable.TextAppearance)
        val style = a.getInt(R.styleable.TextAppearance_android_textStyle, Typeface.BOLD)

        setTextStyle(style)
        a.recycle()

        post(Runnable {
            //setTextSize(getTextSize() * Stylish.getInstance().getFontScale());
        })
    }

    fun setTextStyle(style: Int) {
        BeautyView.setTextStyle(this, style)
    }


    fun setRound(){
        val drawable: StateListDrawable = this.background as StateListDrawable
    }

}
