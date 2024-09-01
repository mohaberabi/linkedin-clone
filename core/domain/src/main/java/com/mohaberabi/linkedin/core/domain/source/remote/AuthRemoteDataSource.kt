package com.mohaberabi.linkedin.core.domain.source.remote

import com.mohaberabi.linkedin.core.domain.model.UserModel


typealias RegisteredUserId = String

interface AuthRemoteDataSource {
    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        name: String,
        lastname: String,
        bio: String,
    ): RegisteredUserId

    suspend fun loginWithEmailAndPassword(
        email: String,
        password: String,
    ): UserModel
}