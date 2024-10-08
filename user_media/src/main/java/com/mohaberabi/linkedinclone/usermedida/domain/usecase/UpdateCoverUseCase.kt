package com.mohaberabi.linkedinclone.usermedida.domain.usecase

import com.mohaberabi.linkedin.core.domain.model.AppFile
import com.mohaberabi.linkedin.core.domain.repository.UserMediaRepository
import javax.inject.Inject


class UpdateCoverUseCase @Inject constructor(
    private val userMediaRepository: UserMediaRepository,
) {
    suspend operator fun invoke(
        file: AppFile,
    ) = userMediaRepository.updateCoverImage(file)
}
