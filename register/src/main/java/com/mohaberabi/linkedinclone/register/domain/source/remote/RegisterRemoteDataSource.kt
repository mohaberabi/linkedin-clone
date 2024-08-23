package com.mohaberabi.linkedinclone.register.domain.source.remote


interface RegisterRemoteDataSource {
    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        name: String,
        lastname: String,
        bio: String,
    ): String
}