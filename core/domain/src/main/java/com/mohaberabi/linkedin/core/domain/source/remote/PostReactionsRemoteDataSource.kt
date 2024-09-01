package com.mohaberabi.linkedin.core.domain.source.remote

import com.mohaberabi.linkedin.core.domain.model.ReactionModel
import com.mohaberabi.linkedin.core.domain.model.ReactionType


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

    suspend fun getUserReactionOnPost(
        postId: String,
        uid: String,
    ): ReactionType?

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

}

