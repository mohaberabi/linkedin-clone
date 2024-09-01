package com.mohaberabi.linkedin.core.domain.usecase.reaction

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult


class PostReactionHandler {
    suspend inline operator fun invoke(
        postId: String,
        reactionType: ReactionType,
        previousReactionType: ReactionType?,
        onUndoReaction: (String) -> EmptyDataResult<ErrorModel>,
        onReactToPost: (String, ReactionType, Int) -> EmptyDataResult<ErrorModel>
    ): EmptyDataResult<ErrorModel> {
        val sameReaction = reactionType == previousReactionType
        return if (sameReaction) {
            onUndoReaction(postId)
        } else {
            val incrementedCount = if (previousReactionType == null) 1 else 0
            onReactToPost(postId, reactionType, incrementedCount)
        }
    }
}