package com.mohaberabi.linkedclone.post_saver.domain

import com.mohaberabi.linedinclone.core.remote_anayltics.domain.LogEventUseCase


internal fun LogEventUseCase.logPostSaved(
    postId: String,
) = this(
    "postSaved",
    params = mapOf(
        "postId" to postId,
    )
)