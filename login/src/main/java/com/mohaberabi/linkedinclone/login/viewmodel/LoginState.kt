package com.mohaberabi.linkedinclone.login.viewmodel

import com.mohaberabi.linkedin.core.domain.model.InputValidator


data class LoginState(
    val email: String = "",
    val password: String = "",
    val emailValidState: InputValidator = InputValidator(),
    val passwordValidState: InputValidator = InputValidator(),
    val loading: Boolean = false,
)