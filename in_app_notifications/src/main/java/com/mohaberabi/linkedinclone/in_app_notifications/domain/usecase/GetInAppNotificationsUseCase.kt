package com.mohaberabi.linkedinclone.in_app_notifications.domain.usecase

import com.mohaberabi.linkedin.core.domain.repository.InAppNotificationsRepository


class GetInAppNotificationsUseCase(
    private val inAppNotificationsRepository: InAppNotificationsRepository,
) {
    suspend operator fun invoke(
        limit: Int = 20,
        lastElementId: String? = null,
    ) = inAppNotificationsRepository.getInAppNotifications(
        limit = limit,
        lastElementId = lastElementId
    )

}