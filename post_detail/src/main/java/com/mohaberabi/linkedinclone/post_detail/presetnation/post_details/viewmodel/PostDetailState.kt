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
    val currentUserUid: String = "",
) {
    val canComment: Boolean
        get() = postComment.trim().isNotEmpty()

}
