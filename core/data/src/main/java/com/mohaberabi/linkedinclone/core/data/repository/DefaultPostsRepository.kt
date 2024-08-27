package com.mohaberabi.linkedinclone.core.data.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.AppFile
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.repository.PostsRepository
import com.mohaberabi.linkedin.core.domain.source.local.compressor.FileCompressor
import com.mohaberabi.linkedin.core.domain.source.local.compressor.FileCompressorFactory
import com.mohaberabi.linkedin.core.domain.source.local.user.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.PostsRemoteDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.StorageClient
import com.mohaberabi.linkedin.core.domain.source.remote.UserReactionId
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class DefaultPostsRepository @Inject constructor(
    private val postRemoteDataSource: PostsRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val storageClient: StorageClient,
    private val fileCompressor: FileCompressorFactory,
) : PostsRepository {
    override suspend fun getPosts(
        limit: Int,
        lastDocId: String?,
    ): AppResult<List<PostModel>, ErrorModel> {
        return AppResult.handle {
            postRemoteDataSource.getPosts(
                lastDocId = lastDocId,
                limit = limit
            )
        }
    }

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
            postRemoteDataSource.addPost(
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

    override fun listenToPosts(
        limit: Int,
        lastDocId: String?,
    ): Flow<List<PostModel>> {
        return userLocalDataSource.getUser()
            .flatMapLatest { user ->
                if (user == null) {
                    flowOf()
                } else {
                    postRemoteDataSource.listenToPosts(
                        limit = limit,
                        lastDocId = lastDocId,
                        userId = user.uid
                    )
                }
            }
    }
}