package com.mohaberabi.linkedin.core.domain.source.remote

import com.mohaberabi.linkedin.core.domain.model.UserModel
import kotlinx.coroutines.flow.Flow


interface UserRemoteDataSource {
    suspend fun updateUser(userModel: UserModel)
    fun listenToUser(uid: String): Flow<UserModel?>
}