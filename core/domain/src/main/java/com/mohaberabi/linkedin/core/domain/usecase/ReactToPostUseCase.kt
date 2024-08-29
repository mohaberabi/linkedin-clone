package com.mohaberabi.linkedin.core.domain.usecase

import com.mohaberabi.linkedin.core.domain.model.ReactionModel
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedin.core.domain.repository.PostsReactionRepository

class ReactToPostUseCase(
    private val reactionRepository: PostsReactionRepository
) {
    suspend operator fun invoke(
        reactionType: ReactionType,
        postId: String,
        incrementCount: Int,
    ) = reactionRepository.reactToPost(
        postId = postId,
        reactionType = reactionType,
        incrementCount = incrementCount
    )
}