package com.mohaberabi.linkedin.core.domain.model


data class PostModel(
    val id: String,
    val createdAtMillis: Long,
    val issuerName: String,
    val issuerUid: String,
    val issuerAvatar: String,
    val issuerBio: String,
    val postData: String,
    val commentsCount: Int,
    val reactionsCount: Int,
    val repostsCount: Int,
    val postAttachedImg: String,
    val currentUserReaction: ReactionModel? = null,
)