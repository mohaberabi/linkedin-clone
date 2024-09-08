package com.mohaberabi.linkedinclone.core.data.source.remote

import com.google.android.gms.common.internal.service.Common
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query.Direction
import com.google.firebase.firestore.snapshots
import com.mohaberabi.linkedin.core.domain.model.SavedPostModel
import com.mohaberabi.linkedin.core.domain.source.remote.SavedPostRemoteDataSource
import com.mohaberabi.linkedin.core.domain.util.CommonParams
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedin.core.domain.util.EndPoints
import com.mohaberabi.linkedinclone.core.data.dto.SavedPostDto
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toSavedPost
import com.mohaberabi.linkedinclone.core.data.util.paginate
import com.mohaberabi.linkedinclone.core.data.util.safeCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseSavedPostsRemoteDataSource @Inject constructor(
    private val dispatchers: DispatchersProvider,
    private val firestore: FirebaseFirestore,
) : SavedPostRemoteDataSource {
    override suspend fun savePost(
        postId: String, uid: String,
    ) {
        withContext(dispatchers.io) {
            val savedDto = SavedPostDto(
                postId = postId,
                savedAtMillis = System.currentTimeMillis()
            )
            firestore.safeCall {
                savedPostsColl(uid)
                    .document(postId)
                    .set(savedDto)
                    .await()
            }
        }
    }

    override suspend fun getSavedPosts(
        limit: Int,
        lastDocId: String?,
        uid: String,
    ): List<SavedPostModel> {
        return withContext(dispatchers.io) {
            firestore.safeCall {
                paginate<SavedPostDto>(
                    limit = limit.toLong(),
                    lastDocId = lastDocId,
                    collection = savedPostsColl(uid = uid),
                    orderBy = CommonParams.SAVED_AT_MILLIS
                ).map { it.toSavedPost() }
            }
        }
    }


    private fun savedPostsColl(uid: String) =
        firestore.collection(EndPoints.USERS)
            .document(uid)
            .collection(EndPoints.SAVED_POSTS)
}