package com.mohaberabi.linkedinclone.post_detail.domain.usecase

import com.mohaberabi.linkedinclone.post_detail.domain.repository.PostDetailRepository

class ListenToPostDetailUseCase(
    private val postDetailRepository: PostDetailRepository,
) {
    operator fun invoke(
        postId: String,
    ) = postDetailRepository.listenToPostDetails(
        postId = postId,
    )
}