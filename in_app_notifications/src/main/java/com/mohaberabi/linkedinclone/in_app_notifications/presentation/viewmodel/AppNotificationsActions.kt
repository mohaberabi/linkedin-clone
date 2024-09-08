package com.mohaberabi.linkedinclone.in_app_notifications.presentation.viewmodel

sealed interface AppNotificationsActions {


    data object LoadMore : AppNotificationsActions
}