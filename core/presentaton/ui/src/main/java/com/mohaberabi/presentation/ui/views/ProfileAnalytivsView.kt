package com.mohaberabi.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.mohaberabi.core.presentation.ui.databinding.ProfileAnaylticsViewBinding


class ProfileAnalytivsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ProfileAnaylticsViewBinding =
        ProfileAnaylticsViewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )


    fun setIcon(drawableRes: Int) {
        binding.analyticsIcon.setImageResource(drawableRes)
    }

    fun setTitle(title: String) {
        binding.analyticsTitle.text = title
    }

    fun setSubtitle(subtitle: String) {
        binding.analyticsSubtitle.text = subtitle
    }
}
