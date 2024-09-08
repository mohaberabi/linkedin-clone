package com.mohaberabi.linkedinclone.core.data.dto.mapper

import com.mohaberabi.linkedin.core.domain.model.ProfileViewerModel
import com.mohaberabi.linkedinclone.core.data.dto.ProfileViewerDto


fun ProfileViewerModel.toViewerDto(
) = ProfileViewerDto(
    uid = uid,
    name = name,
    img = img,
    viewedAtMillis = viewedAtMillis,
    bio = bio
)