package com.mohaberabi.linkedinclone.presentation.activity

import coil.transform.CircleCropTransformation
import com.mohaberabi.linkedinclone.databinding.ActivityMainBinding
import com.mohaberabi.linkedinclone.databinding.NavHeaderBinding
import com.mohaberabi.linkedinclone.presentation.activity.viewmodel.MainActivityState
import com.mohaberabi.presentation.ui.util.cachedImage

fun ActivityMainBinding.bind(
    state: MainActivityState,
    onAvatarClick: () -> Unit,
) {
    val headerView = navView.getHeaderView(0)
    val mainDrawerBinding = NavHeaderBinding.bind(headerView)
    state.user?.let {
        with(mainDrawerBinding) {
            userBio.text = it.bio
            userName.text = "${it.name} ${it.lastname}"
            avatarImage.cachedImage(it.img) {
                transformations(CircleCropTransformation())
            }
            avatarImage.setOnClickListener {
                onAvatarClick()
            }
        }

    }
}
