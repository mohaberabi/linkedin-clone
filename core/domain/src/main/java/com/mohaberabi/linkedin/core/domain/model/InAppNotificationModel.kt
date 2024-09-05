package com.mohaberabi.linkedin.core.domain.model


sealed interface InAppNotificationAction {
    data class Reaction(val reactionType: String)
    data class Comment(val comment: String)
}

data class InAppNotificationModel(
    val id: String,
    val sender: InAppNotificationSender,
    val receiverUid: String,
    val postId: String,
    val action: InAppNotificationAction,
    val createdAtMillis: Long,
)