package com.mohaberabi.linkedinclone.settings.domain.usecase

import com.mohaberabi.linkedinclone.settings.domain.repository.SettingsRepository


class SignOutUseCase(private val settingsRepository: SettingsRepository) {


    suspend operator fun invoke() = settingsRepository.signOut()
}