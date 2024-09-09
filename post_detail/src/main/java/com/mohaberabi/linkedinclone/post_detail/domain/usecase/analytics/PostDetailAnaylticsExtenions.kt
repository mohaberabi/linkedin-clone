package com.mohaberabi.linkedinclone.post_detail.domain.usecase.analytics

import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics


internal fun AppAnalytics.logPostOpened(
    postId: String,
) = logEvent("postOpened", params = mapOf("postId" to postId))


internal fun AppAnalytics.logUserInterestPostReactions(
    postId: String,
) = logEvent("UserInterestPostReactions", params = mapOf("postId" to postId))

internal fun AppAnalytics.logUserInterestPostComments(
    postId: String,
) = logEvent("userInterestToPostComments", params = mapOf("postId" to postId))

internal fun AppAnalytics.logPostGotComment(
    postId: String,
) = logEvent("postGotComment", params = mapOf("postId" to postId))
