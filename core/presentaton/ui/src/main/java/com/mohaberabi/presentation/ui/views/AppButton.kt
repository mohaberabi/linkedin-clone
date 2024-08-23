package com.mohaberabi.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.mohaberabi.core.presentation.ui.databinding.AppButtonBinding

class LoadingButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: AppButtonBinding =
        AppButtonBinding.inflate(LayoutInflater.from(context), this, true)

    fun setButtonText(text: String) {
        binding.buttonText.text = text
    }

    fun setButtonEnabled(isEnabled: Boolean) {
        binding.button.isEnabled = isEnabled
        binding.buttonText.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.buttonText.visibility = View.GONE
            binding.progressBar.visibility = VISIBLE
        } else {
            binding.progressBar.visibility = GONE
            binding.button.isEnabled = true
        }
    }

}
