package com.mohaberabi.linkedin.core.domain.source.remote

import com.mohaberabi.linkedin.core.domain.model.ReactionModel

interface PostReactionsRemoteDataSource {
    suspend fun reactToPost(
        reaction: ReactionModel,
    )
    
    suspend fun getUserReactionOnPost(
        postId: String,
        reactorId: String,
    ): ReactionModel?


    suspend fun getPostReactions(
        postId: String,
        reactionType: String? = null,
        limit: Int = 20,
        lastDocId: String? = null
    ): List<ReactionModel>

}

