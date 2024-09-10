package com.mohaberabi.linkedinclone.user_metadata.usecase

import com.mohaberabi.linkedin.core.domain.repository.UserMetaDataRepository

class MarkAllNotificationsReadUseCase(
    private val userMetaDataRepository: UserMetaDataRepository
) {
    suspend operator fun invoke(
    ) = userMetaDataRepository.markAllNotificationsRead()


}