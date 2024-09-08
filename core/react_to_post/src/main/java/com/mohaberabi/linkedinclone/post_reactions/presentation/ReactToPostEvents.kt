package com.mohaberabi.linkedinclone.post_reactions.presentation

import com.mohaberabi.presentation.ui.util.UiText

sealed interface ReactToPostEvents {


    data class Error(val error: UiText) : ReactToPostEvents
}