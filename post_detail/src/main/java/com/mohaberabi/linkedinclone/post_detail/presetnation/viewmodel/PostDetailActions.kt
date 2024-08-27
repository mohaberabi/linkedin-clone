package com.mohaberabi.linkedinclone.post_detail.presetnation.viewmodel

import com.mohaberabi.linkedin.core.domain.model.ReactionType


sealed interface PostDetailActions {


    data class CommentChanged(
        val comment: String,
    ) : PostDetailActions


    data class ReactOnPost(
        val reactionType: ReactionType,
    ) : PostDetailActions

    data object LoadMoreComments : PostDetailActions
    data object SubmitComment : PostDetailActions

}