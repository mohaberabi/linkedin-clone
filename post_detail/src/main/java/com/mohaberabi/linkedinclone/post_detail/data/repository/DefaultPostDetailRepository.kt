package com.mohaberabi.linkedinclone.post_detail.data.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.error.RemoteError
import com.mohaberabi.linkedin.core.domain.model.PostCommentModel
import com.mohaberabi.linkedin.core.domain.model.PostDetailModel
import com.mohaberabi.linkedin.core.domain.model.ReactionModel
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedin.core.domain.source.local.user.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.PostsRemoteDataSource
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult
import com.mohaberabi.linkedinclone.post_detail.domain.repository.PostDetailRepository
import com.mohaberabi.linkedinclone.post_detail.domain.source.remote.PostCommentRemoteDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.PostReactionsRemoteDataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

class DefaultPostDetailRepository @Inject constructor(
    private val postCommentRemoteDataSource: PostCommentRemoteDataSource,
    private val postsRemoteDataSource: PostsRemoteDataSource,
    private val reactionsRemoteDataSource: PostReactionsRemoteDataSource,
) : PostDetailRepository {
    override suspend fun getPostDetail(
        postId: String,
    ): AppResult<PostDetailModel, ErrorModel> {
        return AppResult.handleResult {
            coroutineScope {
                val post = postsRemoteDataSource.getPost(postId)
                post?.let {
                    val topComments =
                        async {
                            postCommentRemoteDataSource.getPostComments(postId = postId)
                        }.await()
                    val topReactions = async {
                        reactionsRemoteDataSource.getPostReactions(
                            postId = postId,
                        )
                    }.await()
                    val detail = PostDetailModel(
                        topReactions = topReactions,
                        topComments = topComments,
                        post = it,
                    )
                    AppResult.Done(detail)
                } ?: AppResult.Error(ErrorModel(type = RemoteError.UNKNOWN_ERROR))

            }
        }
    }


}

