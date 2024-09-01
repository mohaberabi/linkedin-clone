package com.mohaberabi.linkedinclone.core.data.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.ReactionModel
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedin.core.domain.repository.PostsReactionRepository
import com.mohaberabi.linkedin.core.domain.source.local.posts.PostsLocalDataSource
import com.mohaberabi.linkedin.core.domain.source.local.user.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.PostReactionsRemoteDataSource
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DefaultPostReactionRepository @Inject constructor(
    private val reactionsRemoteDataSource: PostReactionsRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val postsLocalDataSource: PostsLocalDataSource,
) : PostsReactionRepository {
    override suspend fun reactToPost(
        reactionType: ReactionType,
        postId: String,
        incrementCount: Int
    ): EmptyDataResult<ErrorModel> {
        return AppResult.handle {
            val user = userLocalDataSource.getUser().first()!!
            val reaction = ReactionModel(
                reactionType = reactionType,
                reactorId = user.uid,
                reactorImg = user.img,
                reactorBio = user.bio,
                createdAtMillis = System.currentTimeMillis(),
                postId = postId,
                reactorName = "${user.name} ${user.lastname}"
            )
            reactionsRemoteDataSource.reactToPost(
                reaction = reaction,
                incrementCount = incrementCount
            )
            postsLocalDataSource.reactToPost(
                postId = postId,
                reaction = reactionType.name
            )
        }
    }


    override suspend fun undoReactToPost(
        postId: String,
    ): EmptyDataResult<ErrorModel> {
        return AppResult.handle {
            val user = userLocalDataSource.getUser().first()!!
            reactionsRemoteDataSource.undoReactToPost(
                postId = postId,
                reactorId = user.uid,
            )
            postsLocalDataSource.undoReaction(postId)
        }
    }

    override suspend fun getPostReactions(
        postId: String,
        reactionType: String?,
        limit: Int,
        lastDocId: String?
    ): AppResult<List<ReactionModel>, ErrorModel> {
        return AppResult.handle {
            reactionsRemoteDataSource.getPostReactions(
                postId = postId,
                reactionType = reactionType,
                limit = limit,
                lastDocId = lastDocId
            )
        }
    }
}