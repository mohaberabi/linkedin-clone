package com.mohaberabi.linkedin.core.domain.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.AppFile
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult

interface UserMediaRepository {
    suspend fun updateCoverImage(
        image: AppFile,
    ): EmptyDataResult<ErrorModel>

    suspend fun updateAvatarImage(
        image: AppFile,
    ): EmptyDataResult<ErrorModel>
}