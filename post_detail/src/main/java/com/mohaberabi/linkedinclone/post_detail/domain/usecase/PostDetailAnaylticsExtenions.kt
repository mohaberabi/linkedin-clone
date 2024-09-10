package com.mohaberabi.linkedinclone.post_detail.domain.usecase

import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.LogEventUseCase


internal fun LogEventUseCase.logPostOpened(
    postId: String,
) = this("postOpened", params = mapOf("postId" to postId))

internal fun LogEventUseCase.logUserInterestPostReactions(
    postId: String,
) = this("UserInterestPostReactions", params = mapOf("postId" to postId))

internal fun LogEventUseCase.logUserInterestPostComments(
    postId: String,
) = this("userInterestToPostComments", params = mapOf("postId" to postId))

internal fun LogEventUseCase.logPostGotComment(
    postId: String,
) = this("postGotComment", params = mapOf("postId" to postId))
