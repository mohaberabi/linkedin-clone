package com.mohaberabi.linkedinclone.post_detail.domain.usecase

import com.mohaberabi.linkedinclone.post_detail.domain.repository.PostCommentRepository

class GetPostCommentsUseCase(
    private val postCommentRepository: PostCommentRepository,
) {

    suspend operator fun invoke(
        postId: String,
        limit: Int = 20,
        lastDocId: String? = null
    ) = postCommentRepository.getPostComments(
        postId = postId,
        limit = limit,
        lastDocId = lastDocId
    )
}