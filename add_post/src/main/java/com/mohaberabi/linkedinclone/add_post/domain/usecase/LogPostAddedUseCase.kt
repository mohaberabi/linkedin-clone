package com.mohaberabi.linkedinclone.add_post.domain.usecase

import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics
import com.mohaberabi.linkedinclone.add_post.domain.logPostAdded

class LogPostAddedUseCase(
    private val appAnalytics: AppAnalytics
) {

    operator fun invoke() = appAnalytics.logPostAdded()
}
