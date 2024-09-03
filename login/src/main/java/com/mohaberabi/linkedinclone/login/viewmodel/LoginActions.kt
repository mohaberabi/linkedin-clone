package com.mohaberabi.linkedinclone.login.viewmodel

sealed interface LoginActions {


    data class EmailChanged(val email: String) : LoginActions

    data class PasswordChanged(val password: String) : LoginActions
    data object SubmitLogin : LoginActions
}