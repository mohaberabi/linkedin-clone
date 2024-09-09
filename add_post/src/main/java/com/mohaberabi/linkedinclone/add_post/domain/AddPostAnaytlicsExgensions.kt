package com.mohaberabi.linkedinclone.add_post.domain

import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics
import kotlin.math.log


internal fun AppAnalytics.logPostAdded() = logEvent("postAdded")
