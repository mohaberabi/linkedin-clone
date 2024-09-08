package com.mohaberabi.saved_posts.domain.usecase

import com.mohaberabi.linkedin.core.domain.repository.SavedPostsRepository

class GetSavedPostsWithReactionUseCase(
    private val savedPostsRepository: SavedPostsRepository,
) {


    suspend operator fun invoke(
        limit: Int = 10,
        lasDocId: String? = null,
    ) = savedPostsRepository.getSavedPostsWithReactions(
        limit = limit,
        lastDocId = lasDocId
    )
}