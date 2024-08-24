package com.mohaberabi.linkedinclone.usermedida.avatar.viewmodel

import com.mohaberabi.linkedin.core.domain.model.AppFile

data class AvatarState(
    val loading: Boolean = false,
    val avatar: AppFile = AppFile(),
)
