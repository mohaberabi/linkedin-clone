package com.mohaberabi.linkedinclone.register.domain.usecase

import com.mohaberabi.linkedin.core.domain.repository.AuthRepository


class LoginUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
    ) = authRepository.loginWithEmailAndPassword(
        email = email,
        password = password
    )
}