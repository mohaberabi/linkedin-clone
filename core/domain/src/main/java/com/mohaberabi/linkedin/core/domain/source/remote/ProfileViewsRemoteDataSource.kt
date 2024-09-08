package com.mohaberabi.linkedin.core.domain.source.remote

import com.mohaberabi.linkedin.core.domain.model.ProfileViewerModel

interface ProfileViewsRemoteDataSource {


    suspend fun viewSomeoneProfile(
        viewerUid: String,
        viewedUid: String,
    )

    suspend fun getMyProfileViews(
        limit: Int = 20,
        lastDocId: String? = null,
        uid: String
    ): List<ProfileViewerModel>
}