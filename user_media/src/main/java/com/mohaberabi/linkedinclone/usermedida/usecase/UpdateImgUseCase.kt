package com.mohaberabi.linkedinclone.usermedida.usecase

import com.mohaberabi.linkedin.core.domain.model.AppFile
import com.mohaberabi.linkedin.core.domain.repository.UserMediaRepository
import javax.inject.Inject


class UpdateImgUseCase @Inject constructor(
    private val userMediaRepository: UserMediaRepository
) {
    suspend operator fun invoke(
        file: AppFile
    ) = userMediaRepository.updateAvatarImage(file)
}

