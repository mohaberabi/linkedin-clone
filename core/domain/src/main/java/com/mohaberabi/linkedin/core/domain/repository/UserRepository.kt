package com.mohaberabi.linkedin.core.domain.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.linkedin.core.domain.util.AppResult
import kotlinx.coroutines.flow.Flow


interface UserRepository {
    fun listenToUser(uid: String? = null): Flow<UserModel?>

}