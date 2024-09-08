package com.mohaberabi.linkedinclone.core.data.dto.mapper

import com.mohaberabi.linkedin.core.domain.model.SavedPostModel
import com.mohaberabi.linkedinclone.core.data.dto.SavedPostDto


fun SavedPostDto.toSavedPost(
) = SavedPostModel(
    postId = postId,
    savedAtMillis = savedAtMillis,
)