package com.mohaberabi.linkedinclone.post_detail.domain.usecase

import com.mohaberabi.linkedinclone.post_detail.domain.repository.PostDetailRepository

class GetPostDetailUseCase(
    private val postDetailRepository: PostDetailRepository
) {
    suspend operator fun invoke(
        postId: String,
    ) = postDetailRepository.getPostDetail(
        postId = postId,
    )
}