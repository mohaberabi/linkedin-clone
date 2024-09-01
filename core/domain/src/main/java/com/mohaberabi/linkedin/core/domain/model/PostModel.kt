package com.mohaberabi.linkedin.core.domain.model


data class PostModel(
    val id: String,
    val createdAtMillis: Long = System.currentTimeMillis(),
    val issuerName: String,
    val issuerUid: String,
    val issuerAvatar: String,
    val issuerBio: String,
    val postData: String,
    val postAttachedImg: String,
    val commentsCount: Int = 0,
    val reactionsCount: Int = 0,
    val repostsCount: Int = 0,
    val currentUserReaction: ReactionType? = null,
)