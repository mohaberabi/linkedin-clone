package com.mohaberabi.linkedinclone.user_metadata.usecase

import com.mohaberabi.linkedin.core.domain.repository.UserMetaDataRepository

class ListenToUserMetaDataUseCase(
    private val userMetaDataRepository: UserMetaDataRepository,
) {
    operator fun invoke() = userMetaDataRepository.listenToUserMetaData()
}