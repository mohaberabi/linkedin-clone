package com.mohaberabi.linkedinclone.presentation.activity

import coil.transform.CircleCropTransformation
import com.mohaberabi.linkedinclone.presentation.viewmodel.MainActivityState
import com.mohaberabi.linkedinclone.databinding.ActivityMainBinding
import com.mohaberabi.linkedinclone.databinding.NavHeaderBinding
import com.mohaberabi.presentation.ui.util.cached

class MainActivityRenderer(
    private val binding: ActivityMainBinding,
    private val onAvatarClick: () -> Unit,
) {


    fun bind(state: MainActivityState) {

        val headerView = binding.navView.getHeaderView(0)
        val mainDrawerBinding = NavHeaderBinding.bind(headerView)
        state.user?.let {
            with(mainDrawerBinding) {
                userBio.text = it.bio
                userName.text = "${it.name} ${it.lastname}"
                avatarImage.cached(it.img) {
                    transformations(CircleCropTransformation())
                }
                avatarImage.setOnClickListener {
                    onAvatarClick()
                }
            }

        }
    }
}