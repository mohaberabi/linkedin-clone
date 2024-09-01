package com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel

import com.mohaberabi.linkedin.core.domain.model.PostCommentModel
import com.mohaberabi.linkedin.core.domain.model.PostDetailModel
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.model.ReactionModel
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.presentation.ui.util.UiText


enum class PostDetailStatus {
    Loading,
    Error,
    Populated,
}

data class PostDetailState(

    val postComments: List<PostCommentModel> = listOf(),
    val topPostReactions: List<ReactionModel> = listOf(),
    val currentPost: PostModel? = null,
    val state: PostDetailStatus = PostDetailStatus.Loading,
    val error: UiText = UiText.Empty,
    val postComment: String = "",
    val commentLoading: Boolean = false,
) {
    val canComment: Boolean
        get() = postComment.trim().isNotEmpty()

    fun undoReaction() = copy(
        currentPost = currentPost?.copy(
            currentUserReaction = null,
            reactionsCount = currentPost.reactionsCount - 1
        )
    )

    fun submitReaction(reaction: ReactionType) = copy(
        currentPost = currentPost?.copy(
            currentUserReaction = reaction,
            reactionsCount = with(currentPost) {
                if (currentUserReaction == null)
                    reactionsCount + 1 else reactionsCount
            }
        )
    )

    fun addNewComment(comment: PostCommentModel) = copy(
        postComments = listOf(comment) + postComments,
        currentPost = currentPost?.copy(commentsCount = currentPost.commentsCount + 1)
    )
}
