package com.mohaberabi.linkedinclone.core.data.dto

data class ReactionDto(
    val postId: String,
    val reactorId: String,
    val reactorBio: String,
    val reactorImg: String,
    val reactionType: String,
    val createdAtMillis: Long,
    val reactorName: String,
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        0L,
        ""
    )
}