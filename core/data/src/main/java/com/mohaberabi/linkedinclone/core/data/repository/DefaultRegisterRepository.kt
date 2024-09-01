package com.mohaberabi.linkedinclone.core.data.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.linkedin.core.domain.source.local.user.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult
import com.mohaberabi.linkedin.core.domain.repository.AuthRepository
import com.mohaberabi.linkedin.core.domain.source.remote.AuthRemoteDataSource
import javax.inject.Inject

class DefaultRegisterRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : AuthRepository {
    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        name: String,
        lastname: String,
        bio: String
    ): EmptyDataResult<ErrorModel> {
        return AppResult.handle {
            val uid = authRemoteDataSource.createUserWithEmailAndPassword(
                email = email,
                password = password,
                name = name,
                bio = bio,
                lastname = lastname,
            )
            val user = UserModel(
                email = email,
                lastname = lastname,
                name = name,
                bio = bio,
                uid = uid
            )
            userLocalDataSource.saveUser(user)
        }

    }

    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): EmptyDataResult<ErrorModel> {
        return AppResult.handle {
            val user = authRemoteDataSource.loginWithEmailAndPassword(
                email = email,
                password = password
            )
            userLocalDataSource.saveUser(user)
        }
    }
}