package com.mohaberabi.linkedin.core.domain.usecase

import com.mohaberabi.linkedin.core.domain.repository.UserRepository

class GetUserUseCase(
    private val userRepository: UserRepository,
) {


    operator fun invoke() = userRepository.getUser()
}