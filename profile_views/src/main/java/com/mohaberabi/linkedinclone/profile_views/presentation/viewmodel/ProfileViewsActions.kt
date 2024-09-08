package com.mohaberabi.linkedinclone.profile_views.presentation.viewmodel

sealed interface ProfileViewsActions {

    data object Refresh : ProfileViewsActions

    data object LoadMore : ProfileViewsActions
}