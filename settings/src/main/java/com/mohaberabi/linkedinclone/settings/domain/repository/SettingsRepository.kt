package com.mohaberabi.linkedinclone.settings.domain.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult

interface SettingsRepository {

    suspend fun signOut(): EmptyDataResult<ErrorModel>
}