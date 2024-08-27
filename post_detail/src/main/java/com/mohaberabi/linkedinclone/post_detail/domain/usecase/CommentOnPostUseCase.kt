package com.mohaberabi.linkedinclone.post_detail.domain.usecase

import com.mohaberabi.linkedinclone.post_detail.domain.repository.PostCommentRepository

class CommentOnPostUseCase(
    private val commentRepository: PostCommentRepository
) {
    suspend operator fun invoke(
        postId: String,
        comment: String,
    ) = commentRepository.leaveComment(
        postId = postId,
        comment = comment
    )
}