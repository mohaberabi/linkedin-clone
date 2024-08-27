package com.mohaberabi.linkedinclone.core.data.dto


data class PostDto(

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
    val currentUserReaction: ReactionDto? = null,
) {
    constructor() : this(
        "", 0L,
        "", "", "", "", "",
        0, 0,
        0,
        "",
        currentUserReaction = null
    )
}