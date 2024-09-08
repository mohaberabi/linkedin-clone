package com.mohaberabi.linkedinclone.profile.presentation.viewmodel


sealed interface ProfileActions {

    data class LoadOtherUser(val uid: String) : ProfileActions


}