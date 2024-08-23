package com.mohaberabi.linkedinclone.register.domain.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult

interface RegisterRepository {
    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        name: String,
        lastname: String,
        bio: String,
    ): EmptyDataResult<ErrorModel>


}