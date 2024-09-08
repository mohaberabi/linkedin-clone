package com.mohaberabi.linkedinclone.core.data.dto

data class UserMetaDataDto(
    val profileViews: Long = 0,
    val unreadNotifications: Long = 0,
) {
    constructor() : this(
        0,
        0,
    )
}
