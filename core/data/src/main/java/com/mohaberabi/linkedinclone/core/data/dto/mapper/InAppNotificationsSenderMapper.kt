package com.mohaberabi.linkedinclone.core.data.dto.mapper

import com.mohaberabi.linkedin.core.domain.model.InAppNotificationSender
import com.mohaberabi.linkedinclone.core.data.dto.InAppNotificationSenderDto


fun InAppNotificationSenderDto.toInAppNotiSender(
) = InAppNotificationSender(
    name = name,
    uid = uid,
    img = img,
)

fun InAppNotificationSender.toInAppNotiSenderDto(
) = InAppNotificationSenderDto(
    name = name,
    uid = uid,
    img = img,
)