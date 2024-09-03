package com.mohaberabi.linkedinclone.register.presentation.viewmodel


data class RegisterState(

    val name: String = "",
    val lastname: String = "",
    val password: String = "",
    val bio: String = "",
    val email: String = "",
    val loading: Boolean = false,
)