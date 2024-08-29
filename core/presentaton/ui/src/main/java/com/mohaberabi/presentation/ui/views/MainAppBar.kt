package com.mohaberabi.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import coil.transform.CircleCropTransformation
import com.mohaberabi.core.presentation.ui.R
import com.mohaberabi.core.presentation.ui.databinding.MainAppBarBinding
import com.mohaberabi.presentation.ui.util.extension.cachedImage

class MainAppBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {
    private val binding: MainAppBarBinding =
        MainAppBarBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MainAppBar,
            0,
            0
        ).apply {
            try {
                val showAvatar = getBoolean(
                    R.styleable.MainAppBar_showAvatar,
                    true
                )
                val showTextField = getBoolean(
                    R.styleable.MainAppBar_showTextField,
                    false
                )

                val showBackIcon = getBoolean(
                    R.styleable.MainAppBar_showBackArrow,
                    false
                )
                if (showTextField) {
                    binding.searchField.visibility = View.VISIBLE
                } else {
                    binding.searchField.visibility = View.GONE
                }
                binding.searchField.visibility = if (showTextField) View.VISIBLE else View.GONE
                binding.profileAvatar.visibility = if (showAvatar) View.VISIBLE else View.GONE
                if (showBackIcon) {
                    setNavigationIcon(com.mohaberabi.core.presentation.design_system.R.drawable.ic_back)
                } else {
                    navigationIcon = null
                }
            } finally {
                recycle()
            }
        }
    }


    fun setTitle(text: String) {
        binding.toolbarTitle.text = text
        binding.toolbarTitle.visibility = View.VISIBLE
    }

    fun showAvatar(show: Boolean) {
        binding.profileAvatar.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun setOnAvatarClickListener(listener: OnClickListener) {
        binding.profileAvatar.setOnClickListener(listener)
    }

    fun showSearchField(show: Boolean) {
        binding.searchField.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun setOnSearchFieldClickListener(listener: OnClickListener) {
        binding.searchField.setOnClickListener(listener)
    }

    fun setOnNavigationClickListener(listener: OnClickListener) {
        binding.root.setNavigationOnClickListener(listener)
    }

    fun loadImgUrl(url: String) {
        binding.profileAvatar.cachedImage(url) {
            transformations(CircleCropTransformation())
        }
    }
}
