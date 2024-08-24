package com.mohaberabi.linkedin.core.domain.source.remote

import com.mohaberabi.linkedin.core.domain.model.UserModel


interface UserRemoteDataSource {


    suspend fun getUser(uid: String): UserModel?
}