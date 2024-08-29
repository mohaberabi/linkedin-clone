package com.mohaberabi.linkedin.core.domain.usecase

import com.mohaberabi.linkedin.core.domain.repository.PostsReactionRepository

class ListenToUserReactionsUseCase(
    private val postsReactionRepository: PostsReactionRepository,
) {

    operator fun invoke(
        whereIn: List<String>,
    ) = postsReactionRepository.listenToUserReactions(
        whereIn = whereIn
    )
}