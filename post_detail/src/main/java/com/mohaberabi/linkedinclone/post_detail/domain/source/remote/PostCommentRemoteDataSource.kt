package com.mohaberabi.linkedinclone.post_detail.domain.source.remote

import com.mohaberabi.linkedin.core.domain.model.PostCommentModel

interface PostCommentRemoteDataSource {
    suspend fun leaveComment(
        comment: PostCommentModel
    )

    suspend fun getPostComments(
        postId: String,
        lastDocId: String? = null,
        limit: Int = 20
    ): List<PostCommentModel>
}