package com.mohaberabi.linkedin.core.domain.usecase.reaction

import com.mohaberabi.linkedin.core.domain.repository.PostsReactionRepository

class UndoReactToPostUseCase(
    private val reactionRepository: PostsReactionRepository
) {
    suspend operator fun invoke(
        postId: String,
    ) = reactionRepository.undoReactToPost(
        postId = postId,
    )
}