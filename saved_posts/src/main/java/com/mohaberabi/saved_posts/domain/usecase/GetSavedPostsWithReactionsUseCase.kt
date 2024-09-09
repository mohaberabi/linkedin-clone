package com.mohaberabi.saved_posts.domain.usecase

import com.mohaberabi.linkedin.core.domain.repository.SavedPostsRepository

class GetSavedPostsWithReactionsUseCase(
    private val savedPostsRepository: SavedPostsRepository,
) {


    suspend operator fun invoke() = savedPostsRepository.getSavedPostsWithReactions()
}