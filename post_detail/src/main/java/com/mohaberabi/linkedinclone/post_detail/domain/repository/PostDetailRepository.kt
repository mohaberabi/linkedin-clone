package com.mohaberabi.linkedinclone.post_detail.domain.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.PostCommentModel
import com.mohaberabi.linkedin.core.domain.model.PostDetailModel
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult


interface PostDetailRepository {
    suspend fun getPostDetail(
        postId: String,
    ): AppResult<PostDetailModel, ErrorModel>


}