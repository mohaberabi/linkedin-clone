package com.mohaberabi.linkedinclone.core.data.dto


data class AddPostRequest(

    val id: String,
    val createdAtMillis: Long,
    val issuerName: String,
    val issuerUid: String,
    val issuerAvatar: String,
    val issuerBio: String,
    val postData: String,
    val commentsCount: Int = 0,
    val reactionsCount: Int = 0,
    val repostsCount: Int = 0,
    val postAttachedImg: String,
)