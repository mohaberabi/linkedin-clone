package com.mohaberabi.linkedinclone.add_post.domain

import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.LogEventUseCase
import kotlin.math.log


internal fun LogEventUseCase.logPostAdded() = this("postAdded")
