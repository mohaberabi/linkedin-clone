package com.mohaberabi.linkedinclone.presentation.activity.ext

import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics


fun AppAnalytics.logActivityCreated() = logEvent("activityCreated")
fun AppAnalytics.logActivityDestroyed() = logEvent("activityDestroyed")