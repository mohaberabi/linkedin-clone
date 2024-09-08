package com.mohaberabi.linkedin.core.domain.usecase.in_app_noti

import com.mohaberabi.linkedin.core.domain.model.InAppNotificationBehaviour
import com.mohaberabi.linkedin.core.domain.repository.InAppNotificationsRepository

class AddInAppNotificationUseCase(
    private val inAppNotificationsRepository: InAppNotificationsRepository,
) {

    suspend operator fun invoke(
        behaviour: InAppNotificationBehaviour,
        receiverId: String,
    ) = inAppNotificationsRepository.addInAppNotification(
        behaviour = behaviour,
        receiverId = receiverId
    )
}