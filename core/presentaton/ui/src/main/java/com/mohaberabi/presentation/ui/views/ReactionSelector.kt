package com.mohaberabi.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.mohaberabi.core.presentation.ui.databinding.ReactionSelectorBinding
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.presentation.ui.util.extension.icon

class ReactionSelector @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = ReactionSelectorBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )
    private var onReactionSelected: ((ReactionType) -> Unit)? = null

    init {

        setupReactions()
    }

    fun setOnReactionClick(
        onClick: (ReactionType) -> Unit,
    ) {
        this.onReactionSelected = onClick
    }

    private fun setupReactions() {
        binding.linearLayout.removeAllViews()
        ReactionType.entries.filter { it != ReactionType.All }.forEach { reaction ->
            val imageView = ImageView(context).apply {
                setImageDrawable(ContextCompat.getDrawable(context, reaction.icon))
                layoutParams = LayoutParams(
                    resources.getDimensionPixelSize(com.mohaberabi.core.presentation.design_system.R.dimen.margin_large),
                    resources.getDimensionPixelSize(com.mohaberabi.core.presentation.design_system.R.dimen.margin_large)
                ).apply {
                    val marginSize =
                        resources.getDimensionPixelSize(com.mohaberabi.core.presentation.design_system.R.dimen.margin_small)
                    setMargins(marginSize, marginSize, marginSize, marginSize)
                }
                setOnClickListener {
                    onReactionSelected?.invoke(reaction)
                }
            }
            binding.linearLayout.addView(imageView)
        }
    }
}
