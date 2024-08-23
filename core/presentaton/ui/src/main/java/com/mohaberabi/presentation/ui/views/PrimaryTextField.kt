package com.mohaberabi.presentation.ui.views


import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.mohaberabi.core.presentation.ui.R
import com.mohaberabi.core.presentation.ui.databinding.PrimaryTextFieldBinding

class PrimaryTextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: PrimaryTextFieldBinding = PrimaryTextFieldBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    init {

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PrimaryTextField,
            0, 0
        ).apply {
            try {
                val isPassword = getBoolean(
                    R.styleable.PrimaryTextField_isPassword,
                    false
                )
                setObscureText(isPassword)

            } finally {
                recycle()
            }
        }

    }


    fun setError(errorMessage: String?) {
        if (errorMessage.isNullOrEmpty()) {
            binding.textInputLayout.error = null
        } else {
            binding.textInputLayout.error = errorMessage
        }
    }


    fun getText(): String {
        return binding.editText.text.toString()
    }

    fun setText(text: String) {
        binding.editText.setText(text)
    }

    fun setObscureText(isObscure: Boolean) {
        binding.editText.inputType = if (isObscure) {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        } else {
            InputType.TYPE_CLASS_TEXT
        }
        binding.editText.setSelection(binding.editText.text?.length ?: 0)
    }
}
