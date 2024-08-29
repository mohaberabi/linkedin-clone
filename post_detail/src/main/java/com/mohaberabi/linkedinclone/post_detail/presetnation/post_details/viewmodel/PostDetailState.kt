package com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel

import com.mohaberabi.linkedin.core.domain.model.PostDetailModel
import com.mohaberabi.presentation.ui.util.UiText


enum class PostDetailStatus {
    Loading,
    Error,
    Populated,
}

data class PostDetailState(
    val details: PostDetailModel? = null,
    val state: PostDetailStatus = PostDetailStatus.Loading,
    val error: UiText = UiText.Empty,
    val postComment: String = "",
    val commentLoading: Boolean = false,
) {
    val canComment: Boolean
        get() = postComment.trim().isNotEmpty()
}
