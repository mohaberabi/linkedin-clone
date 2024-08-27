package com.mohaberabi.linkedin.core.domain.usecase

import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.linkedin.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class ListenToCurrentUserUseCase(
    private val userRepository: UserRepository,
) {
    operator fun invoke() = userRepository.listenToCurrentUser()

}