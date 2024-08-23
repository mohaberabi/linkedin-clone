package com.mohaberabi.linkedinclone.job_detail.presentation.viewmodel

import com.mohaberabi.linkedin.core.domain.model.JobDetailModel
import com.mohaberabi.presentation.ui.util.UiText


enum class JobDetailStatus {
    Initial,
    Loading,
    Error,
    Populated
}

data class JobDetailState(
    val state: JobDetailStatus = JobDetailStatus.Initial,
    val detail: JobDetailModel? = null,
    val error: UiText = UiText.Empty
)
