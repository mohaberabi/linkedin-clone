package com.mohaberabi.linkedinclone.register.domain

import com.mohaberabi.linedinclone.core.remote_anayltics.domain.LogEventUseCase


internal fun LogEventUseCase.logUserRegistered() = this("userRegistered")