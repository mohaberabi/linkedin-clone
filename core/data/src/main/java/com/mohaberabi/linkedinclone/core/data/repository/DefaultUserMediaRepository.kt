package com.mohaberabi.linkedinclone.core.data.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.AppFile
import com.mohaberabi.linkedin.core.domain.repository.UserMediaRepository
import com.mohaberabi.linkedin.core.domain.source.local.compressor.FileCompressorFactory
import com.mohaberabi.linkedin.core.domain.source.local.user.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.StorageClient
import com.mohaberabi.linkedin.core.domain.source.remote.UserRemoteDataSource
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult
import com.mohaberabi.linkedin.core.domain.util.EndPoints
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

class DefaultUserMediaRepository @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val fileCompressorFactory: FileCompressorFactory,
    private val storageClient: StorageClient
) : UserMediaRepository {
    override suspend fun updateCoverImage(
        image: AppFile,
    ): EmptyDataResult<ErrorModel> {
        return AppResult.handle {
            val user = userLocalDataSource.getUser().first()!!
            val url = uploadCompressedAndGetUrl(
                reference = "${EndPoints.USER_CVR}/${user.uid}",
                file = image
            )
            coroutineScope {
                val remoteUpdate = launch {
                    userRemoteDataSource.updateUser(user.copy(cover = url))
                }
                val localResult = launch { userLocalDataSource.saveUser(user.copy(cover = url)) }
                joinAll(
                    remoteUpdate,
                    localResult,
                )
            }
        }
    }

    override suspend fun updateAvatarImage(
        image: AppFile,
    ): EmptyDataResult<ErrorModel> {
        return AppResult.handle {
            val user = userLocalDataSource.getUser().first()!!
            val url = uploadCompressedAndGetUrl(
                reference = "${EndPoints.USER_IMG}/${user.uid}",
                file = image
            )
            coroutineScope {
                val remoteUpdate = launch {
                    userRemoteDataSource.updateUser(user.copy(img = url))
                }
                val localResult = launch {
                    userLocalDataSource.saveUser(user.copy(img = url))
                }
                joinAll(
                    remoteUpdate,
                    localResult,
                )
            }
        }
    }

    private suspend fun uploadCompressedAndGetUrl(
        file: AppFile,
        reference: String
    ): String {
        val compressed = fileCompressorFactory
            .create(file.type)
            .compress(file)
        val url = storageClient.upload(
            bytes = compressed,
            reference = reference
        )
        return url
    }
}