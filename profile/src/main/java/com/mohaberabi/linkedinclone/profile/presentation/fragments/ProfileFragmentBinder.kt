package com.mohaberabi.linkedinclone.profile.presentation.fragments

import coil.transform.CircleCropTransformation
import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.presentation.ui.util.UiText
import com.mohaberabi.profile.databinding.FragmentProfileBinding
import com.mohaberabi.linkedinclone.profile.presentation.viewmodel.ProfileState
import com.mohaberabi.linkedinclone.profile.presentation.viewmodel.ProfileStatus
import com.mohaberabi.presentation.ui.util.extension.cachedImage
import com.mohaberabi.presentation.ui.util.extension.hideAll
import com.mohaberabi.presentation.ui.util.extension.show
import com.mohaberabi.presentation.ui.util.extension.showIf
import com.mohaberabi.core.presentation.design_system.R.string


fun FragmentProfileBinding.bindFromState(
    state: ProfileState,
) {
    when (state.state) {
        ProfileStatus.Error -> error(state.error)
        ProfileStatus.Done -> {
            if (state.user != null) {
                bindFromUser(
                    user = state.user,
                    isCurrentUser = state.isCurrentUser,
                    userProfileViews = state.profileViews.toInt()
                )
            } else {
                error(state.error)
            }
        }

        else -> {
            hideAll(error, userContent)
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
    error.setErrorTitle(errorText)
}

fun FragmentProfileBinding.bindFromUser(
    user: UserModel,
    isCurrentUser: Boolean = false,
    userProfileViews: Int = 0,
) {
    hideAll(
        error,
        loader
    )

    val connections = root.context.getString(
        string.connections_count,
        user.connections.toString()
    )
    val profileViewsCount = root.context.getString(
        string.profile_views_count,
        userProfileViews.toString()
    )
    val postImpressionsCount = root.context.getString(
        string.post_impressions_count,
        userProfileViews.toString()
    )
    openToButton.showIf(isCurrentUser)
    addSectionButton.showIf(isCurrentUser)
    userConnections.text = connections
    userContent.show()
    userBio.text = user.bio
    userName.text = "${user.name} ${user.lastname}"
    avatarImage.cachedImage(user.img) {
        transformations(CircleCropTransformation())
    }
    coverImage.cachedImage(user.cover)
    profileViews.showIf(isCurrentUser) { setTitle(profileViewsCount) }
    postImpressions.showIf(isCurrentUser) { setTitle(postImpressionsCount) }

}