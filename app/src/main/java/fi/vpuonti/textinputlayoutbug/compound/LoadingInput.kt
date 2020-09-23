package fi.vpuonti.textinputlayoutbug.compound

import android.content.Context
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.res.getDrawableOrThrow
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputLayout.END_ICON_CLEAR_TEXT
import fi.vpuonti.textinputlayoutbug.R


class LoadingInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleSet: Int = 0
) : LinearLayout(context, attrs, defStyleSet) {

    private val mEditText: TextInputEditText
    private val mTextInputLayout: TextInputLayout
    private val mProgressDrawable: Drawable by lazy { getProgressBarDrawable() }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.item_loading_input, this, true)
        mEditText = findViewById(R.id.input)
        mTextInputLayout = findViewById(R.id.container_input)
        mTextInputLayout.endIconMode = END_ICON_CLEAR_TEXT
    }

    fun setLoading(isLoading: Boolean) {
        if (isLoading)
            showLoading()
        else
            hideLoading()
    }

    fun setOnTextChangeListener(listener: (String) -> Unit) {
        mEditText.doOnTextChanged { text, _, _, _ ->
            listener.invoke(text?.toString() ?: "")
        }
    }

    private fun showLoading() {
        mTextInputLayout.endIconMode = TextInputLayout.END_ICON_CUSTOM
        mTextInputLayout.endIconDrawable = mProgressDrawable
        (mTextInputLayout.endIconDrawable as Animatable).start()
    }

    private fun hideLoading() {
        (mTextInputLayout.endIconDrawable as Animatable).stop()
        mTextInputLayout.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
    }

    private fun getProgressBarDrawable(): Drawable {
        val value = TypedValue()
        context.theme.resolveAttribute(android.R.attr.progressBarStyleSmall, value, false)
        val progressBarStyle = value.data
        val attributes = intArrayOf(android.R.attr.indeterminateDrawable)
        val array = context.obtainStyledAttributes(progressBarStyle, attributes)
        val drawable = array.getDrawableOrThrow(0)
        array.recycle()
        return drawable
    }


}