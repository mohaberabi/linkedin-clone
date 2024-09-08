package com.mohaberabi.linkedinclone.in_app_notifications.presentation.viewmodel

import com.mohaberabi.linkedin.core.domain.model.InAppNotificationModel
import com.mohaberabi.presentation.ui.util.UiText


enum class InAppNotiStatus {
    Initial,
    Loading, Error,
    Populated,

}

data class InAppNotificationsState(
    val error: UiText = UiText.Empty,
    val state: InAppNotiStatus = InAppNotiStatus.Initial,

    val notifications: List<InAppNotificationModel> = listOf()
)
