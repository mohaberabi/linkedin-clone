package com.mohaberabi.linkedin.core.domain.source.remote


typealias RegisteredUserId = String

interface RegisterRemoteDataSource {
    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        name: String,
        lastname: String,
        bio: String,
    ): RegisteredUserId
}