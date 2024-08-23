package com.mohaberabi.linkedinclone.add_post.presentation.data.source.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.AppFile
import com.mohaberabi.linkedin.core.domain.source.local.FileCompressor
import com.mohaberabi.linkedin.core.domain.source.local.FileCompressorFactory
import com.mohaberabi.linkedin.core.domain.source.local.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.StorageClient
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult
import com.mohaberabi.linkedinclone.add_post.presentation.domain.repository.AddPostRepository
import com.mohaberabi.linkedinclone.add_post.presentation.domain.source.remote.AddPostRemoteDataSource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class DefaultAddPostRepository @Inject constructor(
    private val addPostRemoteDataSource: AddPostRemoteDataSource,
    private val fileCompressor: FileCompressorFactory,
    private val storageClient: StorageClient,
    private val userLocalDataSource: UserLocalDataSource
) : AddPostRepository {
    override suspend fun addPost(
        postData: String,
        postAttachedImg: AppFile?,
        postId: String,
    ): EmptyDataResult<ErrorModel> {
        return AppResult.handle {
            val fileOrNull = postAttachedImg?.let {
                val compressed = fileCompressor.create(it.type).compress(it)
                storageClient.upload(compressed, it.reference)
            }
            val user = userLocalDataSource.getUser().first()!!
            addPostRemoteDataSource.addPost(
                issuerBio = user.bio,
                issuerUid = user.uid,
                issuerAvatar = user.img,
                issuerName = "${user.name} ${user.lastname}",
                postAttachedImg = fileOrNull,
                postData = postData,
                postId = postId,
            )
        }

    }

}