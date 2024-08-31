package com.mohaberabi.linkedin.core.domain.model

data class PostCommentModel(
    val id: String,
    val commenterId: String,
    val commenterBio: String,
    val commentedAtMillis: Long,
    val commentImg: String,
    val comment: String,
    val postId: String,
    val commentorName: String,
)
