package com.mohaberabi.linkedinclone.core.data.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.InAppNotificationBehaviour
import com.mohaberabi.linkedin.core.domain.model.InAppNotificationModel
import com.mohaberabi.linkedin.core.domain.model.InAppNotificationSender
import com.mohaberabi.linkedin.core.domain.repository.InAppNotificationsRepository
import com.mohaberabi.linkedin.core.domain.source.local.user.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.InAppNotificationsRemoteDataSource
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult
import kotlinx.coroutines.flow.first
import java.util.UUID
import javax.inject.Inject

class DefaultInAppNotificationsRepository @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val inAppNotificationsRemoteDataSource: InAppNotificationsRemoteDataSource,
) : InAppNotificationsRepository {

    override suspend fun getInAppNotifications(
        limit: Int,
        lastElementId: String?
    ): AppResult<List<InAppNotificationModel>, ErrorModel> {
        return AppResult.handle {
            val uid = userLocalDataSource.getUser().first()!!.uid
            inAppNotificationsRemoteDataSource.getInAppNotifications(
                uid = uid,
                limit = limit,
                lastElementId = lastElementId
            )
        }

    }

    override suspend fun addInAppNotification(
        behaviour: InAppNotificationBehaviour,
        receiverId: String,
    ): EmptyDataResult<ErrorModel> {
        return AppResult.handle {
            val user = userLocalDataSource.getUser().first()!!
            val sender = InAppNotificationSender(
                uid = user.uid,
                img = user.img,
                name = "${user.name} ${user.lastname}"
            )
            val noti = InAppNotificationModel(
                id = UUID.randomUUID().toString(),
                receiverUid = receiverId,
                sender = sender,
                action = behaviour,
                createdAtMillis = System.currentTimeMillis(),
            )
            inAppNotificationsRemoteDataSource.addAppNotification(
                notification = noti,
            )
        }
    }

}