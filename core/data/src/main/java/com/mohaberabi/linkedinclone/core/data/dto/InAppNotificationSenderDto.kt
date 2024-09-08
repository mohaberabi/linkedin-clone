package com.mohaberabi.linkedinclone.core.data.dto


data class InAppNotificationSenderDto(
    val name: String,
    val uid: String,
    val img: String,
) {
    constructor() : this(
        "",
        "",
        ""
    )
}
