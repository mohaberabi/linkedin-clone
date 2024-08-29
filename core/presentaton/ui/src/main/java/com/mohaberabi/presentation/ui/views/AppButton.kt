package com.mohaberabi.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.mohaberabi.core.presentation.ui.R
import com.mohaberabi.core.presentation.ui.databinding.AppButtonBinding
import com.mohaberabi.presentation.ui.util.extension.hide
import com.mohaberabi.presentation.ui.util.extension.setVisible
import com.mohaberabi.presentation.ui.util.extension.show


class AppButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null

) : FrameLayout(
    context, attrs
) {

    private val binding: AppButtonBinding = AppButtonBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    init {

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.AppButton,
            0, 0
        ).apply {
            try {
                val buttonText = getString(R.styleable.AppButton_buttonText)
                val buttonWidth = getDimensionPixelSize(
                    R.styleable.AppButton_buttonWidth,
                    LayoutParams.WRAP_CONTENT
                )
                val buttonHeight = getDimensionPixelSize(
                    R.styleable.AppButton_buttonHeight,
                    LayoutParams.WRAP_CONTENT
                )
                val enabled = getBoolean(
                    R.styleable.AppButton_buttonEnabled,
                    true
                )
                val loading = getBoolean(
                    R.styleable.AppButton_buttonLoading,
                    false
                )
                binding.button.isEnabled = enabled
                setLoading(loading)
                binding.button.text = buttonText
                binding.button.layoutParams = binding.button.layoutParams.apply {
                    width = buttonWidth
                    height = buttonHeight
                }
            } finally {
                recycle()
            }
        }
    }


    fun setEnable(
        enable: Boolean,
    ) {
        binding.button.isEnabled = enable

    }

    fun setLoading(isLoading: Boolean) {
        binding.progressBar.setVisible(isLoading)
        binding.button.setVisible(!isLoading)
    }

    fun setText(text: String) {
        binding.button.text = text
    }

    fun setButtonClickListener(listener: View.OnClickListener) {
        binding.button.setOnClickListener {
            if (!binding.progressBar.isVisible) {
                listener.onClick(it)
            }
        }
    }


}