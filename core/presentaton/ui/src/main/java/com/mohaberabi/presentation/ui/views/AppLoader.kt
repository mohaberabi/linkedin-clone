package com.mohaberabi.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.mohaberabi.core.presentation.ui.databinding.AppLoaderBinding


class AppLoader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: AppLoaderBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = AppLoaderBinding.inflate(
            inflater,
            this,
        )
    }


    fun hide() {
        binding.root.visibility = View.GONE
    }

    fun show() {
        binding.root.visibility = View.VISIBLE
    }
}