package com.mohaberabi.linkedin.core.domain.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.AppFile
import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow


interface UserRepository {
    fun listenToCurrentUser(): Flow<UserModel?>
    suspend fun getUser(
        uid: String,
    ): AppResult<UserModel?, ErrorModel>
}