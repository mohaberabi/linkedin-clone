package com.mohaberabi.linkedinclone.post_detail.domain.usecase.analytics

import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics

class LogPostInterestInReactions(
    private val appAnalytics: AppAnalytics
) {

    operator fun invoke(id: String) = appAnalytics.logUserInterestPostReactions(id)
}