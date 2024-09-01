package com.mohaberabi.linkedinclone.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val createdAtMillis: Long,
    val issuerName: String,
    val issuerUid: String,
    val issuerAvatar: String,
    val issuerBio: String,
    val postData: String,
    val postAttachedImg: String,
    val commentsCount: Int = 0,
    val reactionsCount: Int = 0,
    val repostsCount: Int = 0,
    val currentUserReaction: String? = null,
)