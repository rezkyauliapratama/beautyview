package android.beautyview.utility

import android.content.Context
import android.graphics.Typeface
import android.widget.TextView
import java.io.IOException
import java.util.*


object BeautyView {

    private val TYPEFACE: MutableMap<String, Typeface> = HashMap()
    private var FONT_REGULAR = ""
    private var FONT_BOLD = ""
    private var FONT_ITALIC = ""
    private var FONT_BOLD_ITALIC = ""


    private var fontScale = 1.0f

    operator fun set(regular: String, bold: String, italic: String) {
        FONT_REGULAR = regular
        FONT_BOLD = bold
        FONT_ITALIC = italic
    }

    operator fun set(regular: String, bold: String, italic: String, boldItalic: String) {
        FONT_REGULAR = regular
        FONT_BOLD = bold
        FONT_ITALIC = italic
        FONT_BOLD_ITALIC = boldItalic
    }

    fun setFontRegular(fontRegular: String) {
        FONT_REGULAR = fontRegular
    }

    fun setFontBold(fontBold: String) {
        FONT_BOLD = fontBold
    }

    fun setFontItalic(fontItalic: String) {
        FONT_ITALIC = fontItalic
    }

    fun setFontBoldItalic(fontBoldItalic: String) {
        FONT_BOLD_ITALIC = fontBoldItalic
    }


    fun getTypeface(context: Context, font: String, style: Int): Typeface? {

        var typeface: Typeface?
        if (!TYPEFACE.containsKey(font)) {
            try {
                typeface = Typeface.createFromAsset(context.assets,
                        font)
                TYPEFACE.put(font, typeface)

            } catch (e: Exception) {
                typeface = Typeface.defaultFromStyle(style)
            }


        } else
            typeface = TYPEFACE[font]

        return typeface
    }

    fun reqular(context: Context): Typeface? {
        return getTypeface(context, FONT_REGULAR, Typeface.NORMAL)
    }

    fun bold(context: Context): Typeface? {
        return getTypeface(context, FONT_BOLD, Typeface.BOLD)
    }


    fun italic(context: Context): Typeface? {
        return getTypeface(context, FONT_ITALIC, Typeface.ITALIC)
    }

    fun boldItalic(context: Context): Typeface? {
        return getTypeface(context, FONT_BOLD_ITALIC, Typeface.BOLD_ITALIC)
    }

    internal fun isExist(context: Context, font: String): Boolean {
        try {
            return Arrays.asList(*context.resources.assets.list("")).contains(font)
        } catch (e: IOException) {
            return false
        }

    }

    fun getFontScale(): Float {
        return fontScale
    }

    fun setFontScale(fontScale: Float) {
        BeautyView.fontScale = fontScale
    }


    fun getRegular(): Typeface? {
        return TYPEFACE[FONT_REGULAR]
    }

    fun getBold(): Typeface? {
        return TYPEFACE[FONT_BOLD]
    }

    fun getItalic(): Typeface? {
        return TYPEFACE[FONT_ITALIC]
    }

    fun getBoldItalic(): Typeface? {
        return TYPEFACE[FONT_BOLD_ITALIC]
    }

    fun setTextStyle(textView: TextView, style: Int) {
        val font: String
        when (style) {
            Typeface.BOLD -> font = FONT_BOLD
            Typeface.ITALIC -> font = FONT_ITALIC
            Typeface.NORMAL -> font = FONT_REGULAR
            Typeface.BOLD_ITALIC -> font = FONT_BOLD_ITALIC
            else -> font = FONT_REGULAR
        }

        try {
            textView.typeface = getTypeface(textView.context, font, style)
        } catch (e: Exception) {

        }

    }
}
