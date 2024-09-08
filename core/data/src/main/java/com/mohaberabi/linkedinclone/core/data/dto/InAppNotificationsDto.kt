package com.mohaberabi.linkedinclone.core.data.dto


data class InAppNotificationDto(
    val id: String,
    val sender: InAppNotificationSenderDto,
    val receiverUid: String,
    val createdAtMillis: Long,
    val behave: Map<String, Any>
) {
    constructor() : this(
        id = "",
        sender = InAppNotificationSenderDto(),
        receiverUid = "",
        createdAtMillis = 0L,
        behave = mapOf(),
    )
}


