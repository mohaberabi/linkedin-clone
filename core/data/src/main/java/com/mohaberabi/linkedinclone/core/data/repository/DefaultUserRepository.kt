package com.mohaberabi.linkedinclone.core.data.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.linkedin.core.domain.source.local.user.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.repository.UserRepository
import com.mohaberabi.linkedin.core.domain.source.remote.UserRemoteDataSource
import com.mohaberabi.linkedin.core.domain.util.AppResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class DefaultUserRepository @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override fun listenToCurrentUser(): Flow<UserModel?> = userLocalDataSource.getUser()
    override suspend fun getUser(
        uid: String,
    ): AppResult<UserModel?, ErrorModel> {
        return AppResult.handle {
            userRemoteDataSource.getUser(uid)
        }
    }
}