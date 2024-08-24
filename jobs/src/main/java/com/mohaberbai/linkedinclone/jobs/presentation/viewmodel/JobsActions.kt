package com.mohaberbai.linkedinclone.jobs.presentation.viewmodel


sealed interface JobsActions {


    data object LoadMore : JobsActions
}