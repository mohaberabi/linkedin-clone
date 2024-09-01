package com.mohaberbia.linkedinclone.posts.presentation.viewmodel

import com.mohaberabi.linkedin.core.domain.model.ReactionType

sealed interface PostsActions {
    data object LoadMore : PostsActions
    data class ReactToPost(
        val reactionType: ReactionType,
        val postId: String,
        val previousReactionType: ReactionType?
    ) : PostsActions

    data object Refresh : PostsActions

}