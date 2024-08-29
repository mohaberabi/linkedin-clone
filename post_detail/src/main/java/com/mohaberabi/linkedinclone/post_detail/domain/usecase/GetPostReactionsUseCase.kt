package com.mohaberabi.linkedinclone.post_detail.domain.usecase

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.ReactionModel
import com.mohaberabi.linkedin.core.domain.repository.PostsReactionRepository
import com.mohaberabi.linkedin.core.domain.util.AppResult

class GetPostReactionsUseCase(
    private val postReactionsRepository: PostsReactionRepository,
) {
    suspend operator fun invoke(
        postId: String,
        reactionType: String? = null,
        limit: Int = 20,
        lastDocId: String? = null,
    ): AppResult<List<ReactionModel>, ErrorModel> {
        return postReactionsRepository.getPostReactions(
            postId = postId,
            reactionType = reactionType,
            limit = limit,
            lastDocId = lastDocId,
        )
    }
}