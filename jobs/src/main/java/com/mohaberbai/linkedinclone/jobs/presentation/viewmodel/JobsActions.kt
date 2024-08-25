package com.mohaberbai.linkedinclone.jobs.presentation.viewmodel


sealed interface JobsActions {

    data object Refresh : JobsActions

    data object LoadMore : JobsActions
}