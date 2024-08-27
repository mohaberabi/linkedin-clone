package com.mohaberbia.linkedinclone.posts.presentation.viewmodel

import com.mohaberabi.presentation.ui.util.UiText

sealed interface PostsEvents {


    data class Error(val error: UiText) : PostsEvents
    
}