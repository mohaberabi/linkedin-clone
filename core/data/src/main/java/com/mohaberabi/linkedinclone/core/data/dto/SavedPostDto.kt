package com.mohaberabi.linkedinclone.core.data.dto

data class SavedPostDto(

    val postId: String,
    val savedAtMillis: Long,
) {
    constructor() : this("", 0L)
}
