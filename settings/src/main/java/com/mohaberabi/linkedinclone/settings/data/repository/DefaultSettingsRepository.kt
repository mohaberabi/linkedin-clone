package com.mohaberabi.linkedinclone.settings.data.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.source.local.user.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.AuthRemoteDataSource
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult
import com.mohaberabi.linkedinclone.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject


class DefaultSettingsRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : SettingsRepository {
    override suspend fun signOut(): EmptyDataResult<ErrorModel> {

        return AppResult.handle {
            coroutineScope {
                val remoteJob = launch {
                    authRemoteDataSource.signOut()
                }
                val localJob = launch {
                    userLocalDataSource.delete()
                }
                joinAll(
                    localJob,
                    remoteJob,
                )
            }
        }
    }
}