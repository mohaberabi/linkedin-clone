package com.mohaberabi.linkedin.core.domain.source.remote

import com.mohaberabi.linkedin.core.domain.model.UserModel


interface UserRemoteDataSource {


    suspend fun updateUser(userModel: UserModel)
    suspend fun getUser(uid: String): UserModel?
}