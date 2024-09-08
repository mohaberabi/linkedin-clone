package com.mohaberabi.linkedinclone.profile_views.presentation.viewmodel

import com.mohaberabi.linkedin.core.domain.model.ProfileViewerModel
import com.mohaberabi.presentation.ui.util.UiText

enum class ProfileViewStatus {
    Initial,
    Loading,
    Error,
    Populated,
}

data class ProfileViewsState(
    val state: ProfileViewStatus = ProfileViewStatus.Initial,
    val profileViews: List<ProfileViewerModel> = listOf(),
    val error: UiText = UiText.Empty,
)
