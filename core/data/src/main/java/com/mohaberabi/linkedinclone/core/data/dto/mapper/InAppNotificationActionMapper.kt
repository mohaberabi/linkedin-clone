package com.mohaberabi.linkedinclone.core.data.dto.mapper

import com.mohaberabi.linkedin.core.domain.model.NotificationBehaviourType
import com.mohaberabi.linkedin.core.domain.model.InAppNotificationBehaviour
import com.mohaberabi.linkedin.core.domain.model.ReactionType


internal fun InAppNotificationBehaviour.toMap(): Map<String, Any> {
    val typePair = "type" to type.name
    return when (this) {
        is InAppNotificationBehaviour.Comment -> mapOf(
            "comment" to comment,
            "postId" to postId,
        ).plus(typePair)

        is InAppNotificationBehaviour.Reaction -> mapOf(
            "reactionType" to reactionType.name,
            "postId" to postId,
            "postHeader" to postHeader
        ).plus(typePair)


        InAppNotificationBehaviour.Empty -> mapOf(typePair)
        InAppNotificationBehaviour.ViewProfile -> mapOf(typePair)
    }
}

internal fun Map<String, Any>.toInAppNotificationBehaviour(): InAppNotificationBehaviour {
    val type = this["type"]?.let {
        val mappedType = it as String
        NotificationBehaviourType.valueOf(mappedType)
    } ?: NotificationBehaviourType.Empty

    return when (type) {
        NotificationBehaviourType.Comment -> InAppNotificationBehaviour.Comment(
            comment = this["comment"] as String,
            postId = this["postId"] as String? ?: ""
        )

        NotificationBehaviourType.Empty -> InAppNotificationBehaviour.Empty
        NotificationBehaviourType.Reaction -> {
            InAppNotificationBehaviour.Reaction(
                reactionType = ReactionType.valueOf(
                    this["reactionType"] as String,
                ),
                postId = this["postId"] as String? ?: "",
                postHeader = this["postHeader"] as String? ?: ""
            )
        }

        NotificationBehaviourType.ViewProfile -> InAppNotificationBehaviour.ViewProfile
    }
}