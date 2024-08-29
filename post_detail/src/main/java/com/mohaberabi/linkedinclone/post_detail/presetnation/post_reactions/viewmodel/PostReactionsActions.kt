package com.mohaberabi.linkedinclone.post_detail.presetnation.post_reactions.viewmodel

import com.mohaberabi.linkedin.core.domain.model.ReactionType


sealed interface PostReactionsActions {

    data class ReactionTypeChanged(
        val type: ReactionType?,
    ) : PostReactionsActions


    data object LoadMore : PostReactionsActions
}