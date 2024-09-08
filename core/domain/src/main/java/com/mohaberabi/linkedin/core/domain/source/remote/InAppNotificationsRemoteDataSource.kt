package com.mohaberabi.linkedin.core.domain.source.remote

import com.mohaberabi.linkedin.core.domain.model.InAppNotificationModel


interface InAppNotificationsRemoteDataSource {
    suspend fun addAppNotification(
        notification: InAppNotificationModel
    )

    suspend fun getInAppNotifications(
        limit: Int = 20,
        lastElementId: String? = null,
        uid: String
    ): List<InAppNotificationModel>
}