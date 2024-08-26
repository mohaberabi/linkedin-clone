package com.mohaberabi.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.mohaberabi.core.presentation.ui.R
import com.mohaberabi.core.presentation.ui.databinding.SimpleIconButtonBinding

class SimpleIconButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    private val binding = SimpleIconButtonBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SimpleIconButton,
            0, 0
        ).apply {
            try {
                val iconResId = getResourceId(
                    R.styleable.SimpleIconButton_simpleIcon,
                    R.drawable.ic_error
                )
                binding.icon.setImageResource(iconResId)

                val text = getString(
                    R.styleable.SimpleIconButton_simpleText,
                ) ?: ""
                binding.text.text = text
            } finally {
                recycle()
            }
        }
    }

    fun setText(text: String) {
        binding.text.text = text
    }

    fun setIcon(resId: Int) {
        binding.icon.setImageResource(resId)
    }

    fun setClickListener(listener: View.OnClickListener) {
        this.setOnClickListener(listener)
    }
}
