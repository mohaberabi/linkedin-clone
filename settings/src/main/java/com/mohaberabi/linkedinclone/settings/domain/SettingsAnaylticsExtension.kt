package com.mohaberabi.linkedinclone.settings.domain

import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics


internal fun AppAnalytics.logUserSignedOut() {
    logEvent("userSignOut")
}