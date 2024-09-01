package com.mohaberabi.linkedinclone.register.presentation.login.viewmodel

import com.mohaberabi.presentation.ui.util.UiText

sealed interface LoginEvents {


    data object LoggedIn : LoginEvents

    data class Error(val error: UiText) : LoginEvents
}