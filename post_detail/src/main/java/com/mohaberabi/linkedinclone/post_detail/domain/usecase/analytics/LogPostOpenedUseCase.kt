package com.mohaberabi.linkedinclone.post_detail.domain.usecase.analytics

import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics

class LogPostOpenedUseCase(
    private val appAnalytics: AppAnalytics
) {

    operator fun invoke(id: String) = appAnalytics.logPostOpened(id)
}