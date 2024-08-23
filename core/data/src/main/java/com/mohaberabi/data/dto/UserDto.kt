package com.mohaberabi.data.dto

import kotlinx.serialization.Serializable


@Serializable

data class UserDto(
    val uid: String,
    val name: String,
    val lastname: String,
    val bio: String,
    val email: String,
    val img: String,
    val cover: String,
)