package com.mohaberabi.linkedinclone.usermedida.presentation.viewmodel

import com.mohaberabi.linkedin.core.domain.model.AppFile

data class UserMediaState(
    val loading: Boolean = false,
    val avatar: AppFile = AppFile(),
)
