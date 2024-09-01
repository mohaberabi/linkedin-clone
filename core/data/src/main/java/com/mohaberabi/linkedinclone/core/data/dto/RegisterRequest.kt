package com.mohaberabi.linkedinclone.core.data.dto


data class RegisterRequest(
    val uid: String,
    val name: String,
    val lastname: String,
    val email: String,
    val bio: String = "",
    val img: String = "",
    val cover: String = "",
)