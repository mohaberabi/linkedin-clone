package com.mohaberabi.linkedinclone.usermedida.presentation.viewmodel

import com.mohaberabi.presentation.ui.util.UiText


sealed interface UserMediaEvents {
    data class Error(val error: UiText) : UserMediaEvents
    data object Uploaded : UserMediaEvents

}