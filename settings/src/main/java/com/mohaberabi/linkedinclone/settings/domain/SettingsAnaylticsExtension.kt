package com.mohaberabi.linkedinclone.settings.domain

import com.mohaberabi.linedinclone.core.remote_anayltics.domain.LogEventUseCase


internal fun LogEventUseCase.logUserSignedOut() = this("userSignOut")