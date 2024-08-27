package com.mohaberbia.linkedinclone.posts.usecase

import com.mohaberabi.linkedin.core.domain.repository.PostsRepository

class ListenToPostsUseCase(
    private val postsRepository: PostsRepository
) {


    operator fun invoke(
        limit: Int = 20,
        lastDocId: String? = null,
    ) = postsRepository.listenToPosts(
        limit = limit,
        lastDocId = lastDocId
    )
}