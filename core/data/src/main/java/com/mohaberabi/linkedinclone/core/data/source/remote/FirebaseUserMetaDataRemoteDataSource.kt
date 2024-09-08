package com.mohaberabi.linkedinclone.core.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import com.mohaberabi.linkedin.core.domain.model.UserMetaData
import com.mohaberabi.linkedin.core.domain.source.remote.UserMetaDataRemoteDataSource
import com.mohaberabi.linkedin.core.domain.util.CommonParams
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedin.core.domain.util.EndPoints
import com.mohaberabi.linkedinclone.core.data.dto.UserMetaDataDto
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toUserMetaData
import com.mohaberabi.linkedinclone.core.data.util.safeCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseUserMetaDataRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dispatchers: DispatchersProvider,
) : UserMetaDataRemoteDataSource {
    override fun listenToUserMetaData(uid: String): Flow<UserMetaData> {
        return userMetaDoc(uid)
            .snapshots()
            .map { it.toObject<UserMetaDataDto>()?.toUserMetaData() ?: UserMetaData() }
            .flowOn(dispatchers.io)
    }

    override suspend fun markAllNotificationsRead(
        uid: String,
    ) {
        withContext(dispatchers.io) {
            firestore.safeCall {
                val map = mapOf(CommonParams.UNREAD_NOTI to 0)
                userMetaDoc(uid)
                    .update(map)
                    .await()
            }
        }
    }

    private fun userMetaDoc(uid: String) =
        firestore.collection(EndPoints.USERS)
            .document(uid)
            .collection(EndPoints.USER_DATA)
            .document(EndPoints.USER_META_DATA)
}