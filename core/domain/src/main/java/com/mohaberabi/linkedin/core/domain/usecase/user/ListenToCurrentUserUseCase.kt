package com.mohaberabi.linkedin.core.domain.usecase.user

import com.mohaberabi.linkedin.core.domain.repository.UserRepository

class ListenToCurrentUserUseCase(
    private val userRepository: UserRepository,
) {
    operator fun invoke() = userRepository.listenToCurrentUser()

}