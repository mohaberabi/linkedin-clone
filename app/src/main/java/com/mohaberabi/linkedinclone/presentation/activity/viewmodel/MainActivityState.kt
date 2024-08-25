package com.mohaberabi.linkedinclone.presentation.activity.viewmodel

import com.mohaberabi.linkedin.core.domain.model.UserModel


data class MainActivityState(
    val user: UserModel? = null,
    val didLoad: Boolean = false,
)