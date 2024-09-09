package com.mohaberabi.linkedinclone.settings.presentation.viewmodel

import com.mohaberabi.presentation.ui.util.UiText

sealed interface SettingsEvents {


    data class Error(val error: UiText) : SettingsEvents
    data object SignedOut : SettingsEvents
}