package com.mohaberabi.linkedinclone.register.usecase

import com.mohaberabi.linkedin.core.domain.repository.RegisterRepository
import javax.inject.Inject


class RegisterUsecase(
    private val registerRepository: RegisterRepository,
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