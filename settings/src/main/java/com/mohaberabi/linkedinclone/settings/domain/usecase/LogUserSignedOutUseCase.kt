package com.mohaberabi.linkedinclone.settings.domain.usecase

import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics
import com.mohaberabi.linkedinclone.settings.domain.logUserSignedOut

class LogUserSignedOutUseCase(
    private val appAnalytics: AppAnalytics
) {

    operator fun invoke() = appAnalytics.logUserSignedOut()
}