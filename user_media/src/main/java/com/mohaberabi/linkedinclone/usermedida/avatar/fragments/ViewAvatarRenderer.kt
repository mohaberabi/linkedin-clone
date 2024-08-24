package com.mohaberabi.linkedinclone.usermedida.avatar.fragments

import com.mohaberabi.user_media.databinding.FragmentViewAfatarBinding


class ViewAvatarRenderer(
    private val binding: FragmentViewAfatarBinding,
    private val onCancel: () -> Unit,
    private val onConfirm: () -> Unit
) {


    fun bind() {
        binding.cancelButton.setOnClickListener {
            onCancel()
        }
        binding.confirmButton.setOnClickListener {
            onConfirm()
        }
    }
}