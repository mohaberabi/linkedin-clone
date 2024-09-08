package com.mohaberabi.linkedinclone.profile_views.domain.usecase

import com.mohaberabi.linkedin.core.domain.repository.ProfileViewsRepository

class GetProfileViewsUseCase(
    private val profileViewsRepository: ProfileViewsRepository
) {

    suspend operator fun invoke(
        lastDocId: String? = null,
        limit: Int = 20,
    ) = profileViewsRepository.getMyProfileViews(
        lastDocId = lastDocId,
        limit = limit,
    )
}