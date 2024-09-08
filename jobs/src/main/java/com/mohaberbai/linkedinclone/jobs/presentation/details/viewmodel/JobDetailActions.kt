package com.mohaberbai.linkedinclone.jobs.presentation.details.viewmodel

sealed interface JobDetailActions {
    data class JobIdChanged(val id: String) : JobDetailActions
}