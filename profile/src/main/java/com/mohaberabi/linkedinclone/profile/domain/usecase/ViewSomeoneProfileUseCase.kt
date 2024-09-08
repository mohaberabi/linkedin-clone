package com.mohaberabi.linkedinclone.profile.domain.usecase

import com.mohaberabi.linkedin.core.domain.repository.ProfileViewsRepository

class ViewSomeoneProfileUseCase(
    private val profileViewsRepository: ProfileViewsRepository,
) {
    suspend operator fun invoke(
        viewedUid: String,
    ) = profileViewsRepository.viewSomeoneProfile(
        viewedUid = viewedUid,
    )
}