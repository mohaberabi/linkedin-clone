package com.mohaberbai.linkedinclone.jobs.domain

import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.LogEventUseCase


internal fun LogEventUseCase.logJobDetails(
    jobDetailId: String,
) {
    this(
        name = "jobDetailsClicked",
        params = mapOf(
            "jobId" to jobDetailId,
        )
    )
}

internal fun LogEventUseCase.logJobsHasInterests(
) {
    this(
        name = "jobHasInterests",
    )
}