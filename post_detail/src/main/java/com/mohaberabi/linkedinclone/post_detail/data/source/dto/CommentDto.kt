package com.mohaberabi.linkedinclone.post_detail.data.source.dto


data class CommentDto(
    val id: String,
    val commenterId: String,
    val commenterBio: String,
    val commentedAtMillis: Long,
    val commentImg: String,
    val comment: String,
    val postId: String,
    val commentorName: String,
) {
    constructor() : this(
        "",
        "",
        "",
        0L,
        "",
        "",
        "",
        ""
    )
}