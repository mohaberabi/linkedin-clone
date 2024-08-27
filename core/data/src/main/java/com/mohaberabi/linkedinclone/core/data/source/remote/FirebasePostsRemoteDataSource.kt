package com.mohaberabi.linkedinclone.core.data.source.remote

import com.google.android.gms.common.internal.service.Common
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject

import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.model.ReactionModel
import com.mohaberabi.linkedin.core.domain.util.CommonParams
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedin.core.domain.util.EndPoints

import com.mohaberabi.linkedin.core.domain.source.remote.PostsRemoteDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.UserReactionId
import com.mohaberabi.linkedinclone.core.data.dto.AddPostRequest
import com.mohaberabi.linkedinclone.core.data.dto.PostDto
import com.mohaberabi.linkedinclone.core.data.dto.ReactionDto
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toPostModel
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toReactionDto
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toReactionModel
import com.mohaberabi.linkedinclone.core.data.util.listenAndPaginate
import com.mohaberabi.linkedinclone.core.data.util.paginate
import com.mohaberabi.linkedinclone.core.data.util.safeCall
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FirebasePostsRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dispatchers: DispatchersProvider,
) : PostsRemoteDataSource {
    override suspend fun getPosts(
        limit: Int,
        lastDocId: String?,
    ): List<PostModel> {
        return withContext(dispatchers.io) {
            firestore.safeCall {
                paginate<PostDto>(
                    limit = limit.toLong(),
                    lastDocId = lastDocId,
                    coll = EndPoints.Posts,
                    orderBy = CommonParams.CREATED_AT_MILLIS
                ).map {
                    it.toPostModel()
                }
            }
        }

    }

    override suspend fun addPost(
        issuerBio: String,
        issuerAvatar: String,
        postData: String,
        issuerUid: String,
        issuerName: String,
        postAttachedImg: String?,
        postId: String,
    ) {
        return withContext(dispatchers.io) {
            firestore.safeCall {
                val request = AddPostRequest(
                    id = postId,
                    issuerName = issuerName,
                    issuerBio = issuerBio,
                    issuerUid = issuerUid,
                    issuerAvatar = issuerAvatar,
                    createdAtMillis = System.currentTimeMillis(),
                    postData = postData,
                    postAttachedImg = postAttachedImg ?: "",
                )
                collection(EndPoints.Posts).document(request.id).set(request)
            }
        }
    }

    override suspend fun getPost(
        postId: String,
    ): PostModel? {
        return withContext(dispatchers.io) {
            firestore.safeCall {
                collection(EndPoints.Posts)
                    .document(postId).get().await()
                    .toObject(PostDto::class.java)?.toPostModel()
            }
        }
    }

    override fun listenToPosts(
        limit: Int,
        lastDocId: String?,
        userId: UserReactionId
    ): Flow<List<PostModel>> {
        val collection = firestore.collection(EndPoints.Posts)
        val postsFlow = collection
            .orderBy(CommonParams.CREATED_AT_MILLIS, Query.Direction.DESCENDING)
            .limit(limit.toLong())
            .snapshots()
            .map { snaps ->
                snaps.map {
                    it.toObject(PostDto::class.java).toPostModel()
                }
            }.flowOn(dispatchers.io)
        return postsFlow
    }

}