package com.mohaberabi.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.mohaberabi.core.presentation.ui.R
import com.mohaberabi.core.presentation.ui.databinding.ErrorWidgetBinding
import com.mohaberabi.presentation.ui.util.UiText

class ErrorWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ErrorWidgetBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = ErrorWidgetBinding.inflate(inflater, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ErrorWidget, 0, 0)
            val title = typedArray.getString(R.styleable.ErrorWidget_errorTitle)
            val description = typedArray.getString(R.styleable.ErrorWidget_errorDescription)
            setErrorTitle(title ?: "")
            setErrorDescription(description ?: "")

            typedArray.recycle()
        }
    }


    fun setErrorTitle(title: UiText) {
        binding.errorTitle.text = title.asString(context)
    }

    fun setErrorDescription(description: UiText) {
        binding.errorDescription.text = description.asString(context)
    }

    fun setErrorTitle(title: String) {
        binding.errorTitle.text = title
    }

    fun setErrorDescription(description: String) {
        binding.errorDescription.text = description
    }

    fun setOnRetryClickListener(listener: OnClickListener) {
        binding.btnCustom.setOnClickListener(listener)
    }

 
}
