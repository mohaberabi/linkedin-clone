package com.mohaberabi.linkedin.core.domain.repository

import com.mohaberabi.linkedin.core.domain.model.UserModel
import kotlinx.coroutines.flow.Flow


interface UserRepository {
    fun getUser(): Flow<UserModel?>
}