package com.mohaberabi.linkedinclone.profile.presentation.fragments

import android.view.View
import coil.transform.CircleCropTransformation
import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.presentation.ui.util.UiText
import com.mohaberabi.profile.databinding.FragmentProfileBinding
import com.mohaberabi.linkedinclone.profile.presentation.viewmodel.ProfileState
import com.mohaberabi.linkedinclone.profile.presentation.viewmodel.ProfileStatus
import com.mohaberabi.presentation.ui.util.extension.cachedImage

fun FragmentProfileBinding.bind(
    state: ProfileState,
    onImgClicked: () -> Unit,
    onCoverClicked: () -> Unit,
) {
    when (state.state) {
        ProfileStatus.Error -> error(state.error)
        ProfileStatus.Populated -> state.user?.let {
            populated(
                user = it,
                onCoverClicked = onCoverClicked,
                onImgClicked = onImgClicked
            )
        } ?: error(state.error)

        else -> loading()
    }
}

private fun FragmentProfileBinding.loading() {
    loader.show()
    error.hide()
    userContent.visibility = android.view.View.GONE
}

private fun FragmentProfileBinding.error(errorText: UiText) {
    loader.hide()
    error.show()
    error.setErrorTitle(errorText.asString(root.context))
    userContent.visibility = android.view.View.GONE
}

fun FragmentProfileBinding.populated(
    user: UserModel,
    onImgClicked: () -> Unit,
    onCoverClicked: () -> Unit,
) {
    error.hide()
    loader.hide()
    userContent.visibility = android.view.View.VISIBLE
    userBio.text = user.bio
    userName.text = "${user.name} ${user.lastname}"
    avatarImage.apply {
        cachedImage(user.img) {
            transformations(CircleCropTransformation())
        }
        setOnClickListener {
            onImgClicked()
        }
    }
    coverImage.apply {
        cachedImage(user.cover)
        setOnClickListener {
            onCoverClicked()
        }
    }
}