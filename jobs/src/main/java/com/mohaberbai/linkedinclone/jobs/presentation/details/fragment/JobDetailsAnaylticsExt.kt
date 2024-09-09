package com.mohaberbai.linkedinclone.jobs.presentation.details.fragment

import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics


internal fun AppAnalytics.logJobDetails(
    jobDetailId: String,
) {
    logEvent(
        "jobDetailsClicked",
        params = mapOf(
            "jobId" to jobDetailId,
        )
    )
}