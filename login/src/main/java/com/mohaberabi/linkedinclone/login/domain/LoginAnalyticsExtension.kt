package com.mohaberabi.linkedinclone.login.domain

import com.mohaberabi.linedinclone.core.remote_anayltics.domain.LogEventUseCase


internal fun LogEventUseCase.logUserLoggedIn() = this("userLoggedIn")