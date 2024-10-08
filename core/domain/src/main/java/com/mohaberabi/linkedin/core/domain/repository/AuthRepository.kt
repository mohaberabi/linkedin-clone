package com.mohaberabi.linkedin.core.domain.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult

interface AuthRepository {
    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        name: String,
        lastname: String,
        bio: String,
    ): EmptyDataResult<ErrorModel>

    suspend fun loginWithEmailAndPassword(
        email: String,
        password: String,
    ): EmptyDataResult<ErrorModel>
}