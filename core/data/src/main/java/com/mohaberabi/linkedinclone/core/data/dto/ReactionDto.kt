package com.mohaberabi.linkedinclone.core.data.dto

import com.mohaberabi.linkedin.core.domain.model.ReactionType


data class ReactionDto(


    val postId: String,
    val reactorId: String,
    val reactorBio: String,
    val reactorImg: String,
    val reactionType: String,
    val createdAtMillis: Long,
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        0L
    )
}