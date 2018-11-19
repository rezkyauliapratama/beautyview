package android.beautyview.button

import android.annotation.SuppressLint
import android.beautyview.R
import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat

class BeautyButton : BaseButton {

    override fun isSemiTransparent(): Boolean {
        return false
    }

    override fun isNoBackground(): Boolean {
        return false
    }

    override fun isLine(): Boolean {
        return false
    }

    override fun injectComponent(): Boolean {
        return false
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override var defaultRippleColor: Int
        @SuppressLint("PrivateResource")
        get() = ContextCompat.getColor(context, R.color.accent_material_dark)
        set(value) {}

    override var defaultBackgroundColor: Int
        @SuppressLint("PrivateResource")
        get() = ContextCompat.getColor(context, R.color.accent_material_light)
        set(value) {}

    override var defaultRadius: Int
        get() = context.resources.getDimensionPixelSize(R.dimen.default_corner_radius)
        set(value) {}

    override var defaultTextColor: Int
        get() = ContextCompat.getColor(context,R.color.colorBlack_1000)
        set(value) {}

    override var defaultCapitalText: Boolean
        get() = true
        set(value) {}
}