package com.mohaberabi.linkedin.core.domain.model


data class PostDetailModel(
    val post: PostModel?,
    val topComments: List<PostCommentModel>,
    val topReactions: List<ReactionModel>,
)