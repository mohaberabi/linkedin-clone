package com.mohaberabi.linkedinclone.core.data.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.linkedin.core.domain.source.local.user.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.repository.UserRepository
import com.mohaberabi.linkedin.core.domain.source.remote.UserRemoteDataSource
import com.mohaberabi.linkedin.core.domain.util.AppResult
import kotlinx.coroutines.ExperimentalCoroutinesApi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

import javax.inject.Inject


class DefaultUserRepository @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
) : UserRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun listenToUser(
        uid: String?,
    ): Flow<UserModel?> {
        val localUserFlow = userLocalDataSource.getUser()
        return if (uid == null) {
            localUserFlow
        } else {
            localUserFlow.flatMapLatest { user ->
                if (user?.uid == uid) {
                    userLocalDataSource.getUser()
                } else {
                    userRemoteDataSource.listenToUser(uid)
                }
            }
        }
    }

}