package com.mohaberabi.linkedin.core.domain.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.UserMetaData
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow

interface UserMetaDataRepository {
    fun listenToUserMetaData(
    ): Flow<UserMetaData>

    suspend fun markAllNotificationsRead(
    ): EmptyDataResult<ErrorModel>

}