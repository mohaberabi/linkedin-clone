package com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel

import com.mohaberabi.linkedin.core.domain.model.ReactionType


sealed interface PostDetailActions {
    data class CommentChanged(
        val comment: String,
    ) : PostDetailActions

    data class ReactOnPost(
        val reactionType: ReactionType,
        val previousReactionType: ReactionType? = null,
    ) : PostDetailActions

    data object LoadMoreComments : PostDetailActions
    data object SubmitComment : PostDetailActions

}