package com.mohaberabi.linkedinclone.profile.presentation.viewmodel

sealed interface ProfileActions {


    data class GetUser(val uid: String) : ProfileActions
}