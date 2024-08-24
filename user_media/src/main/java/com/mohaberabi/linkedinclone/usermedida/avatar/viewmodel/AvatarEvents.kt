package com.mohaberabi.linkedinclone.usermedida.avatar.viewmodel

import com.mohaberabi.presentation.ui.util.UiText


sealed interface AvatarEvents {
    data class Error(val error: UiText) : AvatarEvents
    data object Uploaded : AvatarEvents

}