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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

class DefaultPostDetailRepository @Inject constructor(
    private val postCommentRemoteDataSource: PostCommentRemoteDataSource,
    private val postsRemoteDataSource: PostsRemoteDataSource,
    private val reactionsRemoteDataSource: PostReactionsRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : PostDetailRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun listenToPostDetails(postId: String): Flow<PostDetailModel> {
        return userLocalDataSource.getUser()
            .flatMapLatest { user ->
                if (user == null) {
                    flowOf()
                } else {
                    val postWithReactionFlow =
                        postsRemoteDataSource.listenToPost(postId = postId, uid = user.uid)
                    val commentsFlow =
                        postCommentRemoteDataSource.listenToPostComments(postId = postId)
                    val reactionsFlow =
                        reactionsRemoteDataSource.listenToPostReactions(postId = postId)
                    postWithReactionFlow.flatMapLatest { post ->
                        post?.let {
                            combine(
                                postWithReactionFlow,
                                commentsFlow,
                                reactionsFlow
                            ) { post, comments, reaction ->
                                PostDetailModel(
                                    topComments = comments,
                                    topReactions = reaction,
                                    post = post
                                )
                            }
                        } ?: flowOf()
                    }

                }
            }
    }


}

