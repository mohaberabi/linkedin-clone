package com.mohaberabi.linkedinclone.job_detail.presentation.viewmodel


sealed interface JobDetailActions {
    data class JobIdChanged(val id: String) : JobDetailActions
}