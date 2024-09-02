package com.mohaberabi.linkedinclone.post_detail.data.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.PostCommentModel
import com.mohaberabi.linkedin.core.domain.source.local.user.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedinclone.post_detail.domain.repository.PostCommentRepository
import com.mohaberabi.linkedinclone.post_detail.domain.source.remote.PostCommentRemoteDataSource
import kotlinx.coroutines.flow.first
import java.util.UUID
import javax.inject.Inject

class DefaultPostCommentRepository @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val postCommentRemoteDataSource: PostCommentRemoteDataSource
) : PostCommentRepository {
    override suspend fun leaveComment(
        postId: String,
        comment: String,
    ): AppResult<PostCommentModel, ErrorModel> {
        return AppResult.handle {
            val user = userLocalDataSource.getUser().first()!!
            val postComment = PostCommentModel(
                id = UUID.randomUUID().toString(),
                postId = postId,
                commenterId = user.uid,
                commenterBio = user.bio,
                commentedAtMillis = System.currentTimeMillis(),
                comment = comment,
                commentImg = user.img,
                commentorName = "${user.name} ${user.lastname}"
            )
            postCommentRemoteDataSource.leaveComment(postComment)
            postComment
        }
    }

    override suspend fun getPostComments(
        postId: String,
        limit: Int,
        lastDocId: String?,
    ): AppResult<List<PostCommentModel>, ErrorModel> {
        return AppResult.handle {
            postCommentRemoteDataSource.getPostComments(
                postId = postId,
                limit = limit,
                lastDocId = lastDocId
            )
        }
    }
}