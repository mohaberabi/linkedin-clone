package com.mohaberabi.linkedin.core.domain.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.ReactionModel
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult
import java.util.concurrent.Flow

interface PostsReactionRepository {
    suspend fun reactToPost(
        reactionType: ReactionType,
        postId: String,
        incrementCount: Int
    ): EmptyDataResult<ErrorModel>

    suspend fun undoReactToPost(
        postId: String,
    ): EmptyDataResult<ErrorModel>


    suspend fun getPostReactions(
        postId: String,
        reactionType: String? = null,
        limit: Int = 20,
        lastDocId: String? = null,
    ): AppResult<List<ReactionModel>, ErrorModel>

    fun listenToUserReactions(
        whereIn: List<String>,
    ): kotlinx.coroutines.flow.Flow<Map<String, ReactionModel?>>
}