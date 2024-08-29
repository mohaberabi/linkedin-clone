package com.mohaberabi.linkedinclone.post_detail.presetnation.post_reactions.viewmodel

import com.mohaberabi.linkedin.core.domain.model.ReactionModel
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.presentation.ui.util.UiText


enum class PostReactionsStatus {
    Initial,
    Loading,
    Error,
    Populated,

}

data class PostReactionsState(
    val reactions: List<ReactionModel> = listOf(),
    val reactionType: ReactionType? = null,
    val state: PostReactionsStatus = PostReactionsStatus.Initial,
    val error: UiText = UiText.Empty
)