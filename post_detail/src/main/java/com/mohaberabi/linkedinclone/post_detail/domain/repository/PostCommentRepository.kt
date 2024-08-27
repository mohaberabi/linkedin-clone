package com.mohaberabi.linkedinclone.post_detail.domain.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.PostCommentModel
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult

interface PostCommentRepository {
    suspend fun leaveComment(
        postId: String,
        comment: String,
    ): AppResult<PostCommentModel, ErrorModel>

    suspend fun getPostComments(
        postId: String,
        limit: Int = 20,
        lastDocId: String? = null
    ): AppResult<List<PostCommentModel>, ErrorModel>
}