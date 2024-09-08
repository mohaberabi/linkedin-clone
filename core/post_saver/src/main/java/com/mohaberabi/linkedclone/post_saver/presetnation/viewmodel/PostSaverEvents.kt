package com.mohaberabi.linkedclone.post_saver.presetnation.viewmodel

import com.mohaberabi.presentation.ui.util.UiText

sealed interface PostSaverEvents {
    data object PostSaved : PostSaverEvents


    data class Error(val error: UiText) : PostSaverEvents
}