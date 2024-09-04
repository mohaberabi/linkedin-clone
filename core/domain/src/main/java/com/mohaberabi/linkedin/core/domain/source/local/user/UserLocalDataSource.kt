package com.mohaberabi.linkedin.core.domain.source.local.user

import com.mohaberabi.linkedin.core.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    suspend fun saveUser(user: UserModel)
    fun getUser(): Flow<UserModel?>
    suspend fun delete()
    suspend fun getUid(): String

}