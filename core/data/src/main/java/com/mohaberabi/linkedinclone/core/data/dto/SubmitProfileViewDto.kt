package com.mohaberabi.linkedinclone.core.data.dto

data class SubmitProfileViewDto(

    val uid: String,
    val viewedAtMillis: Long,
) {
    constructor() : this(":", 0L)
}
