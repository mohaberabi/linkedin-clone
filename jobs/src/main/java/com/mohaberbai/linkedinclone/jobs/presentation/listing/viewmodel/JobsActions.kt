package com.mohaberbai.linkedinclone.jobs.presentation.listing.viewmodel


sealed interface JobsActions {

    data object Refresh : JobsActions

    data object LoadMore : JobsActions
}