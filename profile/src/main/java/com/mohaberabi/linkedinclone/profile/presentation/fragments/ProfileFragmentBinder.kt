package com.mohaberabi.linkedinclone.profile.presentation.fragments

import coil.transform.CircleCropTransformation
import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.presentation.ui.util.UiText
import com.mohaberabi.profile.databinding.FragmentProfileBinding
import com.mohaberabi.linkedinclone.profile.presentation.viewmodel.ProfileState
import com.mohaberabi.linkedinclone.profile.presentation.viewmodel.ProfileStatus
import com.mohaberabi.presentation.ui.util.extension.cachedImage
import com.mohaberabi.presentation.ui.util.extension.hide
import com.mohaberabi.presentation.ui.util.extension.hideAll
import com.mohaberabi.presentation.ui.util.extension.show


fun FragmentProfileBinding.bindWithProfileState(
    state: ProfileState,
) {
    when (state.state) {
        ProfileStatus.Error -> error(state.error)
        ProfileStatus.Populated -> state.user?.let {
            populated(
                user = it,
            )
        } ?: error(state.error)

        else -> {
            hideAll(
                error,
                userContent,
            )
            loader.show()
        }
    }
}


private fun FragmentProfileBinding.error(
    errorText: UiText,
) {
    hideAll(
        userContent,
        loader,
    )
    error.show()
    error.setErrorTitle(errorText.asString(root.context))
}

fun FragmentProfileBinding.populated(
    user: UserModel,
) {
    hideAll(
        error,
        loader
    )
    userContent.show()
    userBio.text = user.bio
    userName.text = "${user.name} ${user.lastname}"

    avatarImage.apply {
        cachedImage(user.img) {
            transformations(CircleCropTransformation())
        }
    }
    coverImage.apply {
        cachedImage(user.cover)
    }
}