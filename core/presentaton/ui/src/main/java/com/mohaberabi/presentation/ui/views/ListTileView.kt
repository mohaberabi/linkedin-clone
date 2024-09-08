package com.mohaberabi.presentation.ui.views

import com.mohaberabi.core.presentation.ui.R
import com.mohaberabi.core.presentation.ui.databinding.ListTileBinding


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat

class ListTileView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {


    private val binding: ListTileBinding = ListTileBinding.inflate(
        LayoutInflater.from(context),
        this,
        true,
    )

    init {

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ListTileView,
            0, 0
        ).apply {
            try {
                val iconResId = getResourceId(R.styleable.ListTileView_icon, 0)
                val text = getString(R.styleable.ListTileView_text)
                if (iconResId != 0) {
                    binding.listTileIcon.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            iconResId
                        )
                    )
                }
                binding.listTileText.text = text
            } finally {
                recycle()
            }
        }
    }

    fun setIcon(iconResId: Int) {
        binding.listTileIcon.setImageDrawable(ContextCompat.getDrawable(context, iconResId))
    }

    fun setText(text: String) {
        binding.listTileText.text = text
    }

    fun setOnClick(listener: OnClickListener) {
        super.setOnClickListener(listener)
    }
}
