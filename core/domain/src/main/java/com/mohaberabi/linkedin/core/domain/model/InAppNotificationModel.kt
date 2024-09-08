package com.mohaberabi.linkedin.core.domain.model


data class InAppNotificationModel(
    val id: String,
    val sender: InAppNotificationSender,
    val receiverUid: String,
    val action: InAppNotificationBehaviour,
    val createdAtMillis: Long,
)