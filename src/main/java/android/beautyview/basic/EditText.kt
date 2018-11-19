package android.beautyview.basic

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.beautyview.R
import android.beautyview.utility.BeautyView
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class EditText : AppCompatEditText {
   
    var calendar: Calendar? = null

    private var is24Hours: Boolean = false
    private var dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private var timeFormat: DateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    private var datePicker: DatePickerDialog? = null
    private var timePicker: TimePickerDialog? = null


    constructor(context: Context) : super(context) {
        setCustomTypeface(context, null)

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setCustomTypeface(context, attrs)


    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setCustomTypeface(context, attrs)

    }

    @SuppressLint("PrivateResource", "CustomViewStyleable")
    private fun setCustomTypeface(context: Context, attrs: AttributeSet?) {
        is24Hours = android.text.format.DateFormat.is24HourFormat(getContext())
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


    override fun setRawInputType(type: Int) {
        when (type) {
            InputType.TYPE_DATETIME_VARIATION_TIME or InputType.TYPE_CLASS_DATETIME -> setupTimePicker()
            InputType.TYPE_DATETIME_VARIATION_DATE or InputType.TYPE_CLASS_DATETIME -> setupDatePicker()
            InputType.TYPE_CLASS_DATETIME -> setupDateTimePicker()
            else -> {
                calendar = null
                super.setRawInputType(type)
            }
        }
    }

    private fun setupDateTimePicker() {
        calendar = Calendar.getInstance()
        disableInput()
        createDatePicker(true)
        createTimePicker(true)
        this.setOnClickListener { view ->
            error = null
            showDatePicker()
            hideKeyboard(view)
        }
        this.onFocusChangeListener = View.OnFocusChangeListener { view, b -> if (b) performClick() }
    }


    private fun setupDatePicker() {
        calendar = Calendar.getInstance()
        disableInput()
        createDatePicker(false)
        this.setOnClickListener { view ->
            error = null
            showDatePicker()
            hideKeyboard(view)
        }
        this.setOnFocusChangeListener { view, b -> if (b) performClick() }
    }


    private fun setupTimePicker() {
        calendar = Calendar.getInstance()
        disableInput()
        createTimePicker(false)
        this.setOnClickListener { view ->
            error = null
            showTimePicker()
            hideKeyboard(view)
        }
        this.onFocusChangeListener = View.OnFocusChangeListener { view, b -> if (b) performClick() }
    }

    private fun createDatePicker(isDateTime: Boolean) {
        datePicker = calendar?.let {
            DatePickerDialog(
                    context,
                DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                    it.set(year, month, day)
                    if (isDateTime)
                        showTimePicker()
                    else
                        displayDate()
                },
                it.get(Calendar.YEAR),
                it.get(Calendar.MONTH),
                it.get(Calendar.DAY_OF_MONTH)
        ) }
    }


    fun createTimePicker(isDateTimePicker: Boolean) {
        timePicker = calendar?.let {
            TimePickerDialog(
                    context,
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        it.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        it.set(Calendar.MINUTE, minute)

                        if (isDateTimePicker)
                            displayDateTime()
                        else
                            displayTime()
                    },
                    it.get(Calendar.HOUR_OF_DAY),
                    it.get(Calendar.MINUTE),
                    is24Hours
            )
        }

    }

    fun hideKeyboard(view: View?) {
        if (view != null) {
            val imm = context
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showKeyboard(view: View?) {
        if (view != null) {
            val imm = context
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, 0)
        }
    }

    private fun showDatePicker() {
        datePicker?.show()
    }

    private fun showTimePicker() {
        timePicker?.show()
    }

    @SuppressLint("SetTextI18n")
    private fun displayDateTime() {
        this.setText(dateFormat.format(calendar?.time) + " " + timeFormat.format(calendar?.time))
    }

    private fun displayDate() {
        this.setText(dateFormat.format(calendar?.time))
    }


    private fun displayTime() {
        this.setText(timeFormat.format(calendar?.time))
    }


    private fun disableInput() {}

    fun setDateFormat(dateFormat: DateFormat) {
        this.dateFormat = dateFormat
    }

    fun setTimeFormat(timeFormat: DateFormat) {
        this.timeFormat = timeFormat
    }
}
