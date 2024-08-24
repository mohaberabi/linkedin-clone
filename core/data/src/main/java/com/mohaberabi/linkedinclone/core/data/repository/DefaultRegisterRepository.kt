package com.mohaberabi.linkedinclone.core.data.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.linkedin.core.domain.source.local.user.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult
import com.mohaberabi.linkedin.core.domain.repository.RegisterRepository
import com.mohaberabi.linkedin.core.domain.source.remote.RegisterRemoteDataSource
import javax.inject.Inject

class DefaultRegisterRepository @Inject constructor(
    private val registerRemoteDataSource: RegisterRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : RegisterRepository {
    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        name: String,
        lastname: String,
        bio: String
    ): EmptyDataResult<ErrorModel> {
        return AppResult.handle {
            val uid = registerRemoteDataSource.createUserWithEmailAndPassword(
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
                cover = "",
                img = "",
                uid = uid
            )
            userLocalDataSource.saveUser(user)
        }

    }
}