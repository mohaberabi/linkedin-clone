package com.mohaberabi.linkedinclone.register.presentation.viewmodel

import com.mohaberabi.presentation.ui.util.UiText


sealed interface RegisterEvents {


    data object Registered : RegisterEvents

    data class Error(val error: UiText) : RegisterEvents
}