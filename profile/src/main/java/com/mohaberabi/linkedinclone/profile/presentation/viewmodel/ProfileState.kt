package com.mohaberabi.linkedinclone.profile.presentation.viewmodel

import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.presentation.ui.util.UiText


enum class ProfileStatus {
    Initial,
    Loading,
    Error,
    Done,
}


data class ProfileState(
    val user: UserModel? = null,
    val error: UiText = UiText.Empty,
    val state: ProfileStatus = ProfileStatus.Initial,
    val isCurrentUser: Boolean = false,
    val profileViews: Long = 0,
    val postImpressions: Long = 0,
)