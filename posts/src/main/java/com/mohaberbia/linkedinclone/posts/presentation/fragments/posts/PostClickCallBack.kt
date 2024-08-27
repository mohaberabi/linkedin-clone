package com.mohaberbia.linkedinclone.posts.presentation.fragments.posts

import com.mohaberabi.linkedin.core.domain.model.PostModel


data class PostClickCallBacks(
    val onClick: (PostModel) -> Unit = {},
    val onLongClickLike: (PostModel) -> Unit = {},
    val onLikeClick: (PostModel) -> Unit = {},
)