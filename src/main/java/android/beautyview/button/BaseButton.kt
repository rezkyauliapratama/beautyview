package android.beautyview.button

import android.annotation.SuppressLint
import android.beautyview.R
import android.beautyview.utility.BeautyView
import android.beautyview.utility.RippleEffect
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import android.graphics.Typeface
import android.util.Log
import androidx.annotation.ColorInt
import androidx.core.view.ViewCompat

abstract class BaseButton : AppCompatButton {

    /**
     * Ripple Color
     */
    @ColorInt
    var mRippleColor : Int = 0

    /**
     * Background Color
     */
    @ColorInt
    var mBackgroundColor: Int = 0

    /**
    * Text Color
    */
    @ColorInt
    var mTextColor: Int = 0

    /**
    * Radius round corner
    */
    private var mCornerRadius: Float = 0.toFloat()

    /**
     * Capital letter
     */
    private var mIsCapital: Boolean = true


    abstract var defaultRippleColor : Int
    abstract var defaultBackgroundColor : Int
    abstract var defaultRadius : Int
    abstract var defaultTextColor: Int
    abstract var defaultCapitalText: Boolean

    abstract fun injectComponent() : Boolean
    abstract fun isLine() : Boolean
    abstract fun isSemiTransparent() : Boolean
    abstract fun isNoBackground(): Boolean

    sealed class Intention {
        object Line : Intention()
        object SemiTransparent : Intention()
        object NoBackground : Intention()
    }

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs,0)

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    @SuppressLint("CustomViewStyleable", "PrivateResource")
    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {

        var intention: Intention ? = null
        when {
            isLine() -> intention = Intention.Line
            isSemiTransparent() -> intention = Intention.SemiTransparent
            isNoBackground() -> {
                intention = Intention.NoBackground
            }
        }

        if (injectComponent()){
            mBackgroundColor = defaultBackgroundColor

            mRippleColor = defaultRippleColor

            mCornerRadius = defaultRadius.toFloat()

            mTextColor = defaultTextColor

            mIsCapital = defaultCapitalText

        }else{
            getXMLAttributes(attrs,defStyle)
        }


        if (!mIsCapital){
            this.transformationMethod = null
        }
        RippleEffect.addRippleEffect(this,true,mBackgroundColor,mRippleColor,mCornerRadius,intention)

        ViewCompat.setElevation(this, resources.getDimensionPixelSize(R.dimen.default_elevation).toFloat())
        ViewCompat.setZ(this, resources.getDimensionPixelSize(R.dimen.default_elevation).toFloat())
        this.setTextColor(mTextColor)

        requestLayout()
    }

    @SuppressLint("PrivateResource", "CustomViewStyleable")
    private fun getXMLAttributes(attrs: AttributeSet?, defStyle: Int){
        val a = context.obtainStyledAttributes(
                attrs, R.styleable.BaseButton, defStyle, 0)

        mBackgroundColor = a.getColor(R.styleable.BaseButton_background_color,
                defaultBackgroundColor)

        mRippleColor = a.getColor(R.styleable.BaseButton_ripple_color,
                defaultRippleColor)

        mCornerRadius = a.getDimensionPixelOffset(R.styleable.BaseButton_corner_radius,
                defaultRadius).toFloat()

        mIsCapital = if (isNoBackground()) {
            a.getBoolean(R.styleable.BaseButton_text_capital,
                    false)
        }else {
            a.getBoolean(R.styleable.BaseButton_text_capital,
                    true)
        }

        a.recycle()

        val b = context.obtainStyledAttributes(attrs, R.styleable.TextAppearance)
        val style = b.getInt(R.styleable.TextAppearance_android_textStyle, Typeface.NORMAL)
        mTextColor = b.getColor(R.styleable.TextAppearance_android_textColor, defaultTextColor)
        setTextStyle(style)
        b.recycle()

    }
    private fun setTextStyle(style: Int) {
        BeautyView.setTextStyle(this, style)
    }


}
