package com.mohaberabi.linkedinclone.usermedida.profile_picture.viewmodel

import com.mohaberabi.presentation.ui.util.UiText


sealed interface ProfilePictureEvents {
    data class Error(val error: UiText) : ProfilePictureEvents
    data object Uploaded : ProfilePictureEvents

}