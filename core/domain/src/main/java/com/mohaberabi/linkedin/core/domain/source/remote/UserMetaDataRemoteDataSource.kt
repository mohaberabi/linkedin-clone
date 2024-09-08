package com.mohaberabi.linkedin.core.domain.source.remote

import com.mohaberabi.linkedin.core.domain.model.UserMetaData
import kotlinx.coroutines.flow.Flow


interface UserMetaDataRemoteDataSource {
    fun listenToUserMetaData(
        uid: String,
    ): Flow<UserMetaData>

    suspend fun markAllNotificationsRead(
        uid: String,
    )

}