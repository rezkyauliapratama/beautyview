package android.beautyview

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.beautyview.basic.EditText
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.StateListDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.constraintlayout.widget.ConstraintLayout
import android.text.InputType
import android.view.inputmethod.EditorInfo
import java.lang.StringBuilder


/**
 * Created by Rezky Aulia Pratama on 28/10/18.
 */
class BeautyEditText : ConstraintLayout{


    /**
     * Ripple Color
     */
    @ColorInt
    var mLineColor : Int = 0

    /**
     * Background Color
     */
    @ColorInt
    var mBackgroundColor: Int = 0

    /**
     * Radius round corner
     */
    private var mCornerRadius: Float = 0.toFloat()

    /**
     * Icon
     */
    private var mImageLeft: Drawable ? = null
    private var mImageRight: Drawable ? = null

    /**
     * Radius round corner
     */
    private var mMultipleLine: Boolean = false

    /**
     * Hints for the ediitext
     */
    private var mHints: String = ""

    var view: View? = null
    var editText: EditText? = null
    var imageViewLeft: ImageView? = null
    var imageViewRight: ImageView? = null
    var layoutImageRight : FrameLayout ? =null
    var layoutImageLeft : FrameLayout ? =null

    private val defaultPadding = resources.getDimensionPixelSize(R.dimen.default_padding);


    constructor(context: Context) : super(context) {
        init(context, null, 0)

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs,0)

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)

    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        initView(context)

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            //TODO add function to decor below Lollipop
        } else {
            decorationV21(attrs,defStyleAttr)
        }

        val layoutImageDrawable = GradientDrawable()
        layoutImageDrawable.shape = GradientDrawable.RECTANGLE
        layoutImageDrawable.cornerRadius = mCornerRadius
//        layoutImageDrawable.paint.color = mBackgroundColor

        if (mImageLeft != null){
            imageViewLeft?.let {
                it.setImageDrawable(mImageLeft)
                it.visibility = View.VISIBLE
                it.background = layoutImageDrawable
            }

        }else{
            layoutImageLeft?.visibility = View.GONE
        }

        if (mImageRight!= null){
            imageViewRight?.let {
                it.setImageDrawable(mImageRight)
                it.visibility = View.VISIBLE
                it.background = layoutImageDrawable
            }

        }else{
            layoutImageRight?.visibility = View.GONE

        }

        val backgroundDrawable = GradientDrawable()
        backgroundDrawable.shape = GradientDrawable.RECTANGLE
        backgroundDrawable.cornerRadius = mCornerRadius
        backgroundDrawable.setStroke(2,mLineColor)
        backgroundDrawable.setColor(mBackgroundColor)

        val focuseDrawable = GradientDrawable()
        focuseDrawable.shape = GradientDrawable.RECTANGLE
        focuseDrawable.cornerRadius = mCornerRadius
        focuseDrawable.setStroke(4,mLineColor)
        focuseDrawable.setColor(mBackgroundColor)

        val stateDrawable = StateListDrawable()
        val stateFocused= intArrayOf(android.R.attr.state_focused)
        val stateNormal = intArrayOf(android.R.attr.state_enabled)

        stateDrawable.addState(stateFocused,focuseDrawable)
        stateDrawable.addState(stateNormal,backgroundDrawable)
        this.background = stateDrawable

        val edittextLayoutDrawable = GradientDrawable()
        edittextLayoutDrawable.cornerRadius = mCornerRadius


        editText?.let{

            it.background = edittextLayoutDrawable

            it.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    stateDrawable.state = stateNormal
                } else {
                    stateDrawable.state = stateFocused

                }
            }


            if (mMultipleLine){
                it.imeOptions = EditorInfo.IME_ACTION_DONE
                it.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                it.setHorizontallyScrolling(false)
                it.setSingleLine(false)
            }else{
                it.imeOptions = EditorInfo.IME_ACTION_DONE
                it.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                it.setHorizontallyScrolling(true)
                it.setSingleLine(true)
            }

            it.hint = mHints
        }



    }

    private fun initView(context: Context) {
        view = LayoutInflater.from(context).inflate(R.layout.edittext_border_with_icon, this, false)

       addView(view)

        editText = view?.findViewById(R.id.edittext)
        imageViewRight = view?.findViewById(R.id.imageRight)
        imageViewLeft= view?.findViewById(R.id.imageLeft)
        layoutImageLeft= view?.findViewById(R.id.layoutImageLeft)
        layoutImageRight= view?.findViewById(R.id.layoutImageRight)

    }

    @SuppressLint("CustomViewStyleable")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun decorationV21(attrs: AttributeSet?,defStyle: Int){
        val a = getContext().obtainStyledAttributes(
                attrs, R.styleable.BeautyEditText, defStyle, 0)

        mImageLeft = a.getDrawable(R.styleable.BeautyEditText_icon_left)
        mImageRight = a.getDrawable(R.styleable.BeautyEditText_icon_right)

        mBackgroundColor = a.getColor(R.styleable.BeautyEditText_background_color,
                ContextCompat.getColor(context, R.color.colorBackgroundSemi))

        mLineColor = a.getColor(R.styleable.BeautyEditText_line_color,
                ContextCompat.getColor(context,R.color.colorLine))

        mCornerRadius = a.getDimensionPixelOffset(R.styleable.BeautyEditText_corner_radius,
                resources.getDimensionPixelSize(R.dimen.default_corner_radius)).toFloat()

        mHints = a.getDimensionPixelOffset(R.styleable.BeautyEditText_hints,
                R.string.default_hints).toString()

        mMultipleLine = a.getBoolean(R.styleable.BeautyEditText_multiple_line,
                false)
        a.recycle()

    }

    var text:Editable?
        get() = editText?.text
        set(value) {
            editText?.text = value
        }

    fun addTextChangedListener(afterTextChangedListener: (String) -> Unit){
        editText?.afterTextChanged {
            afterTextChangedListener(it)
        }
    }


    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }
/*
    fun stateColor(color: Int): ColorStateList {
        return ColorStateList(arrayOf(intArrayOf()), intArrayOf(color,color,color, color))
    }*/
}



/* private fun initView(context: Context) {

//        view = LayoutInflater.from(context).inflate(R.layout.edittext_border_with_icon, this, false)

//        addView(view)


//        imageViewLeft = view?.findViewById(R.id.imageLeft)
//        imageViewRight = view?.findViewById(R.id.imageRight)
//        editText = view?.findViewById(R.id.edittext)
    *//* setPadding(
                if (super.getPaddingLeft()== 0) defaultPadding else super.getPaddingLeft(),
                if (super.getPaddingTop()== 0) defaultPadding else super.getPaddingTop(),
                if (super.getPaddingRight()== 0) defaultPadding else super.getPaddingRight(),
                if (super.getPaddingBottom()== 0) defaultPadding else super.getPaddingBottom()
                )*//*



        gravity = Gravity.CENTER



        val height = measuredHeight
        val width= measuredWidth
        layoutParams = LinearLayout.LayoutParams(width, height)
        orientation = LinearLayout.HORIZONTAL

        if (mImageLeft != null){
            imageViewLeft = ImageView(context)
            val param = FrameLayout.LayoutParams(
                    resources.getDimensionPixelSize(R.dimen.default_icon_size),
                    resources.getDimensionPixelSize(R.dimen.default_icon_size))
            param.gravity = Gravity.CENTER
            imageViewLeft?.layoutParams = param
            imageViewLeft?.setImageDrawable(mImageLeft)

            imageViewLeft?.let { addView(getLayoutImage(context, it)) }
        }


        editText = EditText(context)
//        val param = LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,0.8F)
        val param = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)
        param.setMargins(defaultPadding,
                defaultPadding,
                defaultPadding,
                defaultPadding)
        editText?.layoutParams = param
        editText?.minEms = 12
        editText?.requestLayout()

        addView(editText)



        if (mImageRight != null){
            imageViewRight = ImageView(context)
            val param = FrameLayout.LayoutParams(
                    resources.getDimensionPixelSize(R.dimen.default_icon_size),
                    resources.getDimensionPixelSize(R.dimen.default_icon_size))
            param.gravity = Gravity.CENTER
            imageViewRight?.layoutParams = param
            imageViewRight?.setImageDrawable(mImageRight)

            imageViewRight?.let { addView(getLayoutImage(context, it)) }
        }

    }*/

/*

private fun getLayoutImage(context: Context, imageView: ImageView): FrameLayout{
    val frameLayout = FrameLayout(context)
    val param = LinearLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)
    param.gravity = Gravity.CENTER

    frameLayout.layoutParams = param
    frameLayout.setPadding(
            defaultPadding,
            defaultPadding,
            defaultPadding,
            defaultPadding
    )

    frameLayout.addView(imageView)
    return frameLayout
}*/
