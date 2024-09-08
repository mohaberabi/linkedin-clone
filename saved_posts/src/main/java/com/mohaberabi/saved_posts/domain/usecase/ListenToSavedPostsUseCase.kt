package com.mohaberabi.saved_posts.domain.usecase

import com.mohaberabi.linkedin.core.domain.repository.SavedPostsRepository

class ListenToSavedPostsUseCase(
    private val savedPostsRepository: SavedPostsRepository,
) {
    operator fun invoke() = savedPostsRepository.listenToSavedPosts()
}