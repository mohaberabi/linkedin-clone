package com.mohaberabi.linkedinclone.profile.presentation.viewmodel

import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.presentation.ui.util.UiText


enum class ProfileStatus {
    Initial,
    Loading,
    Error,
    Populated,

}

data class ProfileState(
    val user: UserModel? = null,
    val canEdit: Boolean = false,
    val error: UiText = UiText.Empty,
    val state: ProfileStatus = ProfileStatus.Initial
)