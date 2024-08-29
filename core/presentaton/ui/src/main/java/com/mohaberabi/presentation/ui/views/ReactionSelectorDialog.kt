package com.mohaberabi.presentation.ui.views

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.mohaberabi.core.presentation.ui.databinding.ReactionSelectorDialogBinding
import com.mohaberabi.linkedin.core.domain.model.ReactionType


fun Context.showReactionDialog(
    onReactionSelected: (ReactionType) -> Unit
) {
    val dialog = Dialog(this)
    val binding = ReactionSelectorDialogBinding.inflate(LayoutInflater.from(this))
    dialog.setContentView(binding.root)
    binding.reactionSelector.setOnReactionClick { reaction ->
        onReactionSelected(reaction)
        dialog.dismiss()
    }
    dialog.show()
}
