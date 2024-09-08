package com.mohaberabi.linkedin.core.domain.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.InAppNotificationBehaviour
import com.mohaberabi.linkedin.core.domain.model.InAppNotificationModel
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult

interface InAppNotificationsRepository {


    suspend fun getInAppNotifications(
        limit: Int = 20,
        lastElementId: String? = null,
    ): AppResult<List<InAppNotificationModel>, ErrorModel>


    suspend fun addInAppNotification(
        behaviour: InAppNotificationBehaviour,
        receiverId: String,
    ): EmptyDataResult<ErrorModel>
}