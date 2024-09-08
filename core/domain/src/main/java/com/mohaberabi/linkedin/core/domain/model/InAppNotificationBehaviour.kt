package com.mohaberabi.linkedin.core.domain.model

sealed interface InAppNotificationBehaviour {
    val type: NotificationBehaviourType

    data class Reaction(
        val reactionType: ReactionType,
        override val type: NotificationBehaviourType = NotificationBehaviourType.Reaction,
        val postId: String,
        val postHeader: String,
    ) : InAppNotificationBehaviour

    data class Comment(
        val comment: String,
        override val type: NotificationBehaviourType = NotificationBehaviourType.Comment,
        val postId: String,
    ) : InAppNotificationBehaviour

    data object Empty : InAppNotificationBehaviour {
        override val type: NotificationBehaviourType
            get() = NotificationBehaviourType.Empty
    }

    data object ViewProfile : InAppNotificationBehaviour {
        override val type: NotificationBehaviourType
            get() = NotificationBehaviourType.ViewProfile
    }
}
