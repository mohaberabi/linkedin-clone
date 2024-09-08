package com.mohaberabi.linkedin.core.domain.usecase.user

import com.mohaberabi.linkedin.core.domain.repository.UserRepository

class ListenToUserUseCase(
    private val userRepository: UserRepository,
) {
    operator fun invoke(
        uid: String? = null,
    ) = userRepository.listenToUser(uid = uid)

}