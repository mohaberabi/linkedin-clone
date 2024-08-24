package com.mohaberabi.linkedin.core.domain.usecase

import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.linkedin.core.domain.repository.UserRepository

interface ListenToCurrentUserUseCase {
    operator fun invoke(): kotlinx.coroutines.flow.Flow<UserModel?>
}