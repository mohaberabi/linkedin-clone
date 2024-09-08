package com.mohaberabi.linkedinclone.core.data.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.ProfileViewerModel
import com.mohaberabi.linkedin.core.domain.repository.ProfileViewsRepository
import com.mohaberabi.linkedin.core.domain.source.local.user.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.ProfileViewsRemoteDataSource
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult
import javax.inject.Inject

class DefaultProfileViewersRepository @Inject constructor(
    private val profileViewesRemoteDataSource: ProfileViewsRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : ProfileViewsRepository {
    override suspend fun viewSomeoneProfile(
        viewedUid: String,
    ): EmptyDataResult<ErrorModel> {
        return AppResult.handle {
            val uid = userLocalDataSource.getUid()
            profileViewesRemoteDataSource.viewSomeoneProfile(
                viewedUid = viewedUid,
                viewerUid = uid
            )
        }
    }

    override suspend fun getMyProfileViews(
        lastDocId: String?,
        limit: Int,
    ): AppResult<List<ProfileViewerModel>, ErrorModel> {
        return AppResult.handle {
            val uid = userLocalDataSource.getUid()
            profileViewesRemoteDataSource.getMyProfileViews(
                lastDocId = lastDocId,
                limit = limit,
                uid = uid
            )
        }
    }
}