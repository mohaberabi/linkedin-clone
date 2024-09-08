package com.mohaberabi.linkedinclone.core.data.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.UserMetaData
import com.mohaberabi.linkedin.core.domain.repository.UserMetaDataRepository
import com.mohaberabi.linkedin.core.domain.source.local.user.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.UserMetaDataRemoteDataSource
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class DefaultUserMetaDataRepository @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userMetaDataRemoteDataSource: UserMetaDataRemoteDataSource,
) : UserMetaDataRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun listenToUserMetaData(): Flow<UserMetaData> {
        return userLocalDataSource.getUser()
            .flatMapLatest { user ->
                user?.let {
                    userMetaDataRemoteDataSource.listenToUserMetaData(it.uid)
                } ?: flowOf(UserMetaData())
            }
    }

    override suspend fun markAllNotificationsRead(
    ): EmptyDataResult<ErrorModel> {
        return AppResult.handle {
            val uid = userLocalDataSource.getUid()
            userMetaDataRemoteDataSource.markAllNotificationsRead(uid)
        }
    }
}