package com.mohaberabi.linkedinclone.jobs.presentation.viewmodel


sealed interface JobsActions {


    data object LoadMore : JobsActions
}