package com.mohaberabi.linkedinclone.core.data.dto.mapper

import com.mohaberabi.linkedin.core.domain.model.UserMetaData
import com.mohaberabi.linkedinclone.core.data.dto.UserMetaDataDto


fun UserMetaDataDto.toUserMetaData() = UserMetaData(
    profileViews = profileViews,
    unreadNotifications = unreadNotifications,
)