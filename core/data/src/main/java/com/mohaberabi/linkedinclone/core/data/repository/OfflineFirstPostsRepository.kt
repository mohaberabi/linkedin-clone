package com.mohaberabi.linkedinclone.core.data.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.AppFile
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.repository.PostsRepository
import com.mohaberabi.linkedin.core.domain.source.local.compressor.FileCompressorFactory
import com.mohaberabi.linkedin.core.domain.source.local.posts.PostsLocalDataSource
import com.mohaberabi.linkedin.core.domain.source.local.user.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.PostReactionsRemoteDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.PostsRemoteDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.StorageClient
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class OfflineFirstPostsRepository @Inject constructor(
    private val postRemoteDataSource: PostsRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val storageClient: StorageClient,
    private val fileCompressor: FileCompressorFactory,
    private val postsLocalDataSource: PostsLocalDataSource,
    private val reactionsRemoteDataSource: PostReactionsRemoteDataSource,
) : PostsRepository {
    override suspend fun getPostsWithUserReaction(
        limit: Int,
        lastDocId: String?
    ): EmptyDataResult<ErrorModel> {
        return AppResult.handle {
            val posts = postRemoteDataSource.getPosts(
                limit = limit,
                lastDocId = lastDocId
            )
            val postsIds = posts.map { it.id }
            val uid = userLocalDataSource.getUser().first()!!.uid
            val reactions =
                reactionsRemoteDataSource.getUsersReactionsOnPosts(
                    postIds = postsIds,
                    uid = uid
                )
            val upsertPosts = posts.map {
                it.copy(currentUserReaction = reactions[it.id])
            }
            postsLocalDataSource.upsertPosts(upsertPosts)

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
            val post = PostModel(
                issuerBio = user.bio,
                issuerUid = user.uid,
                issuerAvatar = user.img,
                issuerName = "${user.name} ${user.lastname}",
                postAttachedImg = fileOrNull ?: "",
                postData = postData,
                id = postId,
            )
            postRemoteDataSource.addPost(
                issuerBio = post.issuerBio,
                issuerUid = post.issuerUid,
                issuerAvatar = post.issuerAvatar,
                issuerName = post.issuerName,
                postAttachedImg = post.postAttachedImg,
                postData = post.postData,
                postId = post.id,
            )
            postsLocalDataSource.upsertPost(post)
        }

    }

    override fun listenToPosts(
    ): Flow<List<PostModel>> = postsLocalDataSource.getPosts()

}