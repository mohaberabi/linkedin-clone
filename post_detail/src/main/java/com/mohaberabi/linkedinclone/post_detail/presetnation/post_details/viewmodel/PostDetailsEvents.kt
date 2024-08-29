package com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel

import com.mohaberabi.presentation.ui.util.UiText

sealed interface PostDetailsEvents {


    data class Error(
        val error: UiText,
    ) : PostDetailsEvents


}