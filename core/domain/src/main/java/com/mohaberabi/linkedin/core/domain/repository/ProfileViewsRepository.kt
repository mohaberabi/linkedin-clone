package com.mohaberabi.linkedin.core.domain.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.ProfileViewerModel
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult


interface ProfileViewsRepository {


    suspend fun viewSomeoneProfile(
        viewedUid: String,
    ): EmptyDataResult<ErrorModel>

    suspend fun getMyProfileViews(
        lastDocId: String? = null,
        limit: Int = 20,
    ): AppResult<List<ProfileViewerModel>, ErrorModel>
}