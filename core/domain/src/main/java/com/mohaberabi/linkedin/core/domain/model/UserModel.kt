package com.mohaberabi.linkedin.core.domain.model


data class UserModel(
    val uid: String,
    val name: String,
    val lastname: String,
    val bio: String,
    val email: String,
    val img: String = "",
    val cover: String = "",
)