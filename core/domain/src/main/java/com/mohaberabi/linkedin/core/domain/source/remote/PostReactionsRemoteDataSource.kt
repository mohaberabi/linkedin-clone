package com.mohaberabi.linkedin.core.domain.source.remote

import com.mohaberabi.linkedin.core.domain.model.ReactionModel
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import kotlinx.coroutines.flow.Flow


private typealias PostId = String

interface PostReactionsRemoteDataSource {
    suspend fun reactToPost(
        reaction: ReactionModel,
        incrementCount: Int,
    )

    suspend fun undoReactToPost(
        postId: String,
        reactorId: String,
    )

    suspend fun getUsersReactionsOnPosts(
        uid: String,
        postIds: List<String>
    ): Map<PostId, ReactionType>

    suspend fun getPostReactions(
        postId: String,
        reactionType: String? = null,
        limit: Int = 20,
        lastDocId: String? = null,
    ): List<ReactionModel>

    fun listenToPostReactions(
        postId: String,
        limit: Int = 20,
    ): Flow<List<ReactionModel>>
}

