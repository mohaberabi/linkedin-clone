package com.mohaberabi.linkedinclone.core.data.dto

data class ProfileViewerDto(
    val name: String,
    val uid: String,
    val viewedAtMillis: Long,
    val img: String,
    val bio: String,
) {
    constructor() : this("", "", 0L, "", "")
}
