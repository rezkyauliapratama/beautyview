package android.beautyview.utility

import android.annotation.TargetApi
import android.beautyview.button.BaseButton
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.*
import android.util.StateSet
import androidx.core.view.ViewCompat
import android.os.Build
import android.view.View


object RippleEffect {

    fun addRippleEffect(view: View, rippleEnabled: Boolean, backgroundColor: Int, rippleColor: Int, radius: Float, intention: BaseButton.Intention?) {

        if (rippleEnabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Create RippleDrawable
            view.background = getPressedColorRippleDrawable(
                    backgroundColor,
                    rippleColor,
                    radius,
                    intention)


        } else if (rippleEnabled) {

            //Create Selector for pre Lollipop
            ViewCompat.setBackground(view,
                    createStateListDrawable(backgroundColor, rippleColor,radius,intention))

        } else {

            //Ripple Disabled
            view.background = ColorDrawable(backgroundColor)

        }


    }

    private fun createStateListDrawable(backgroundColor: Int, rippleColor: Int, radius: Float, intention: BaseButton.Intention?): StateListDrawable {

        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(
                intArrayOf(android.R.attr.state_pressed),
                createDrawable(rippleColor,radius))
        stateListDrawable.addState(
                StateSet.WILD_CARD,
                createDrawable(backgroundColor,radius,intention))
        return stateListDrawable
    }

    private fun createDrawable(background: Int, radius: Float, intention: BaseButton.Intention?): Drawable {
        val gradientDrawable = GradientDrawable()
        checkIntention(background,intention, gradientDrawable)
        gradientDrawable.cornerRadius = radius

        return gradientDrawable
    }

    private fun createDrawable(background: Int, radius: Float): Drawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(background)
        gradientDrawable.cornerRadius = radius
        return gradientDrawable
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun getPressedColorRippleDrawable(
            normalColor: Int, pressedColor: Int, radius: Float, intention: BaseButton.Intention?): RippleDrawable {

        val rippleDrawable = RippleDrawable(
                getPressedColorSelector(pressedColor),
                getGradientDrawableFromColor(normalColor,radius,intention), getGradientDrawableFromColor(Color.WHITE,radius))

        return rippleDrawable
    }


    private fun getPressedColorSelector(
             pressedColor: Int): ColorStateList {

        return ColorStateList(arrayOf(intArrayOf()), intArrayOf(pressedColor,pressedColor,pressedColor, Color.WHITE))

    }

    private fun getGradientDrawableFromColor(
            color: Int,
            radius: Float): GradientDrawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.cornerRadius = radius
        gradientDrawable.setColor(color)
        return gradientDrawable
    }

    private fun getGradientDrawableFromColor(color: Int, radius: Float, intention: BaseButton.Intention?): GradientDrawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.cornerRadius = radius
        checkIntention(color,intention,gradientDrawable)

        return gradientDrawable
    }

    private fun getGradientDrawableFromColor(
            color: Int): GradientDrawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(color)
        return gradientDrawable
    }

    private fun checkIntention(color: Int,intention: BaseButton.Intention?, gradientDrawable: GradientDrawable): GradientDrawable{
        when (intention) {
            is BaseButton.Intention.Line-> {
                gradientDrawable.setColor(Color.TRANSPARENT)
                gradientDrawable.setStroke(2,color)
            }
            is BaseButton.Intention.SemiTransparent -> {
                gradientDrawable.setColor(color)
                gradientDrawable.alpha = 100
            }
            is BaseButton.Intention.NoBackground  -> {
                gradientDrawable.setColor(Color.TRANSPARENT)
            }
            else -> {
                gradientDrawable.setColor(color)
            }
        }
        return gradientDrawable
    }
}