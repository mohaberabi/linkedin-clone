package com.mohaberabi.linkedinclone.register.presentation.register.viewmodel

sealed interface RegisterActions {

    data class BioChanged(val bio: String) : RegisterActions


    data class EmailChanged(val email: String) : RegisterActions
    data class PasswordChanged(val password: String) : RegisterActions
    data class NameChanged(val name: String) : RegisterActions
    data class LastNameChanged(val lastname: String) : RegisterActions

    data object OnRegisterClick : RegisterActions
}