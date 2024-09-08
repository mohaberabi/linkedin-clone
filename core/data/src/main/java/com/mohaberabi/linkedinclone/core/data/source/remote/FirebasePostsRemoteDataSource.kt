package com.mohaberabi.linkedinclone.core.data.source.remote

import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.util.CommonParams
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedin.core.domain.util.EndPoints
import com.mohaberabi.linkedin.core.domain.source.remote.PostsRemoteDataSource
import com.mohaberabi.linkedinclone.core.data.dto.AddPostRequest
import com.mohaberabi.linkedinclone.core.data.dto.PostDto
import com.mohaberabi.linkedinclone.core.data.dto.ReactionDto
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toPostModel
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toReactionModel
import com.mohaberabi.linkedinclone.core.data.util.paginate
import com.mohaberabi.linkedinclone.core.data.util.safeCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
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

    override suspend fun getPostsByIds(
        postsIds: List<String>,
    ): List<PostModel> {
        return withContext(dispatchers.io) {
            firestore.collection(EndPoints.Posts)
                .where(Filter.inArray(FieldPath.documentId(), postsIds))
                .get()
                .await()
                .mapNotNull { it.toObject<PostDto>().toPostModel() }
        }
    }


    override fun listenToPost(
        postId: String,
        uid: String
    ): Flow<PostModel?> {
        val postDoc = firestore.collection(EndPoints.Posts).document(postId)
        val postFlow = postDoc.snapshots()
            .map { it.toObject(PostDto::class.java)?.toPostModel() }
        val userReactionOnPostFlow =
            postDoc.collection(EndPoints.REACTIONS)
                .document(uid).snapshots()
                .map { it.toObject(ReactionDto::class.java)?.toReactionModel() }
        return combine(
            postFlow, userReactionOnPostFlow,
        ) { post, reaction ->
            post?.copy(currentUserReaction = reaction?.reactionType)
        }.flowOn(dispatchers.io)

    }


}