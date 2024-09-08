package com.mohaberabi.linkedinclone.core.data.source.remote

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.mohaberabi.linkedin.core.domain.model.InAppNotificationModel
import com.mohaberabi.linkedin.core.domain.source.remote.InAppNotificationsRemoteDataSource
import com.mohaberabi.linkedin.core.domain.util.CommonParams
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedin.core.domain.util.EndPoints
import com.mohaberabi.linkedinclone.core.data.dto.InAppNotificationDto
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toInAppNotificaitonDto
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toInAppNotification
import com.mohaberabi.linkedinclone.core.data.util.paginate
import com.mohaberabi.linkedinclone.core.data.util.safeCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseInAppNotificationsRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dispatcher: DispatchersProvider,
) : InAppNotificationsRemoteDataSource {
    override suspend fun addAppNotification(
        notification: InAppNotificationModel,
    ) {
        return withContext(dispatcher.io) {
            firestore.safeCall {
                val atGlobal = collection(EndPoints.USERS)
                    .document(notification.receiverUid)
                    .collection(EndPoints.NOTIFICATIONS)
                    .document(notification.id)
                val atUser =
                    collection(EndPoints.USERS)
                        .document(notification.receiverUid)
                        .collection(EndPoints.USER_DATA)
                        .document(EndPoints.USER_META_DATA)
                runTransaction { txn ->
                    val map = mapOf(CommonParams.UNREAD_NOTI to FieldValue.increment(1))
                    txn.set(atGlobal, notification.toInAppNotificaitonDto())
                    txn.update(atUser, map)
                }.await()
            }
        }
    }

    override suspend fun getInAppNotifications(
        limit: Int,
        lastElementId: String?,
        uid: String
    ): List<InAppNotificationModel> {
        return withContext(dispatcher.io) {
            firestore.safeCall {
                val collection = collection(EndPoints.USERS)
                    .document(uid)
                    .collection(EndPoints.NOTIFICATIONS)
                paginate<InAppNotificationDto>(
                    limit = limit.toLong(),
                    orderBy = CommonParams.CREATED_AT_MILLIS,
                    lastDocId = lastElementId,
                    collection = collection,
                ).map { it.toInAppNotification() }
            }
        }
    }
}