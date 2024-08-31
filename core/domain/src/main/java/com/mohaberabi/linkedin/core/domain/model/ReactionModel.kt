package com.mohaberabi.linkedin.core.domain.model


data class ReactionModel(
    val postId: String,
    val reactorId: String,
    val reactorBio: String,
    val reactorImg: String,
    val reactionType: ReactionType,
    val createdAtMillis: Long,
    val reactorName: String,
)