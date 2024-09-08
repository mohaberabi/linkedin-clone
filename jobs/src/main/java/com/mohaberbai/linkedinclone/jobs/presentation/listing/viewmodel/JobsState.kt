package com.mohaberbai.linkedinclone.jobs.presentation.listing.viewmodel

import com.mohaberabi.linkedin.core.domain.model.JobModel
import com.mohaberabi.presentation.ui.util.UiText


enum class JobsStatus {
    Initial,
    Loading,
    Error,
    Populated,
}

data class JobsState(
    val jobs: List<JobModel> = listOf(),
    val error: UiText = UiText.Empty,
    val state: JobsStatus = JobsStatus.Initial,
    val userImg: String = "",
)