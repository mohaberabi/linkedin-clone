package com.mohaberabi.linkedin.core.domain.model


data class ProfileViewerModel(
    val name: String,
    val uid: String,
    val viewedAtMillis: Long,
    val img: String,
    val bio: String,
)

