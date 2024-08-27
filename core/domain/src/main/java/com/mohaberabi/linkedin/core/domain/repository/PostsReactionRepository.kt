package com.mohaberabi.linkedin.core.domain.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.ReactionModel
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult

interface PostsReactionRepository {
    suspend fun reactToPost(
        reactionType: ReactionType,
        postId: String,
    ): EmptyDataResult<ErrorModel>
}