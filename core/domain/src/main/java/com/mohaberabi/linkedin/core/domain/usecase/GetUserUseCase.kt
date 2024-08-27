package com.mohaberabi.linkedin.core.domain.usecase

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.linkedin.core.domain.repository.UserRepository
import com.mohaberabi.linkedin.core.domain.util.AppResult


class GetUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        uid: String,
    ): AppResult<UserModel?, ErrorModel> = userRepository.getUser(uid = uid)
}