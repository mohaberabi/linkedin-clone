package com.mohaberabi.linkedinclone.core.data.dto.mapper

import com.mohaberabi.linkedin.core.domain.model.InAppNotificationModel
import com.mohaberabi.linkedinclone.core.data.dto.InAppNotificationDto


fun InAppNotificationModel.toInAppNotificaitonDto(): InAppNotificationDto {


    val dto = InAppNotificationDto(
        id = id,
        sender = sender.toInAppNotiSenderDto(),
        receiverUid = receiverUid,
        createdAtMillis = createdAtMillis,
        behave = action.toMap(),
    )
    return dto
}

fun InAppNotificationDto.toInAppNotification(): InAppNotificationModel {

    val inAppNoti = InAppNotificationModel(
        id = id,
        sender = sender.toInAppNotiSender(),
        createdAtMillis = createdAtMillis,
        receiverUid = receiverUid,
        action = behave.toInAppNotificationBehaviour()
    )
    return inAppNoti
}