package com.mohaberabi.linkedinclone.usermedida.profile_picture.viewmodel

import com.mohaberabi.linkedin.core.domain.model.AppFile

data class ProfilePictureState(
    val loading: Boolean = false,
    val avatar: AppFile = AppFile(),
)
