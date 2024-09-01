package com.mohaberabi.linkedinclone.register.domain.usecase

import com.mohaberabi.linkedin.core.domain.repository.AuthRepository


class RegisterUsecase(
    private val registerRepository: AuthRepository,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        name: String,
        lastname: String,
        bio: String,
    ) = registerRepository.createUserWithEmailAndPassword(
        email = email,
        password = password,
        lastname = lastname,
        bio = bio,
        name = name
    )
}