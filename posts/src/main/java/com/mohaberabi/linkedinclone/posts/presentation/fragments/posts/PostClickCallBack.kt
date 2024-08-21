package com.mohaberabi.linkedinclone.posts.presentation.fragments.posts

import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.util.UnitCallBack


data class PostClickCallBacks(
    val onClick: (PostModel) -> Unit = {},
    val onLikeClick: (PostModel) -> Unit = {},
)