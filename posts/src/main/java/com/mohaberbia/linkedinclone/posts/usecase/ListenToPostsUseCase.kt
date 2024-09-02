package com.mohaberbia.linkedinclone.posts.usecase

import com.mohaberabi.linkedin.core.domain.repository.PostsRepository

class ListenToPostsUseCase(
    private val postsRepository: PostsRepository
) {
    operator fun invoke(
    ) = postsRepository.listenToPosts(
    )
}