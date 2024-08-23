package com.mohaberabi.linkedinclone.add_post.presentation.viewmodel

import com.mohaberabi.presentation.ui.util.UiText


sealed interface AddPostEvents {


    data object Posted : AddPostEvents

    data class Error(val error: UiText) : AddPostEvents
}