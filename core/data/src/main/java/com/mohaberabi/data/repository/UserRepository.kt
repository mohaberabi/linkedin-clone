package com.mohaberabi.data.repository

import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.linkedin.core.domain.source.local.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class DefaultUserRepository @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
) : UserRepository {
    override fun getUser(): Flow<UserModel?> = userLocalDataSource.getUser()
}