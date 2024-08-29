package com.mohaberabi.linkedin.core.domain.source.remote

import com.mohaberabi.linkedin.core.domain.model.ReactionModel
import kotlinx.coroutines.flow.Flow

interface PostReactionsRemoteDataSource {
    suspend fun reactToPost(
        reaction: ReactionModel,
        incrementCount: Int,
    )

    suspend fun undoReactToPost(
        postId: String,
        reactorId: String,
    )

    fun listenToUserPostReactions(
        uid: String,
        whereIn: List<String>
    ): Flow<Map<String, ReactionModel?>>

    suspend fun getPostReactions(
        postId: String,
        reactionType: String? = null,
        limit: Int = 20,
        lastDocId: String? = null,
    ): List<ReactionModel>

}

