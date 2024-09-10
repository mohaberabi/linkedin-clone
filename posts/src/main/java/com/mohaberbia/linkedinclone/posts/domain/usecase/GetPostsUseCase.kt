package com.mohaberbia.linkedinclone.posts.domain.usecase

import com.mohaberabi.linkedin.core.domain.repository.PostsRepository
import javax.inject.Inject

class GetPostsUseCase(
    private val postsRepository: PostsRepository,
) {

    suspend operator fun invoke(
        limit: Int = 20,
        lastDocId: String? = null,
    ) = postsRepository.getPostsWithUserReaction(
        limit = limit,
        lastDocId = lastDocId
    )
}