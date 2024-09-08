package com.mohaberabi.linkedinclone.post_reactions.presentation

import com.mohaberabi.linkedin.core.domain.model.ReactionType

data class PostReactionParams(
    val postId: String,
    val reactionType: ReactionType,
    val postOwnerId: String,
    val previousReactionType: ReactionType? = null,
    val postHeader: String,
)

sealed interface ReactToPostActions {
    data class ReactToPost(
        val params: PostReactionParams,
    ) : ReactToPostActions
}