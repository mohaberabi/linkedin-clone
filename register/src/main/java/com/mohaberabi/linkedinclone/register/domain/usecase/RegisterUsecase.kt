package com.mohaberabi.linkedinclone.register.domain.usecase

import com.mohaberabi.linkedinclone.register.domain.repository.RegisterRepository
import javax.inject.Inject


class RegisterUsecase @Inject constructor(
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