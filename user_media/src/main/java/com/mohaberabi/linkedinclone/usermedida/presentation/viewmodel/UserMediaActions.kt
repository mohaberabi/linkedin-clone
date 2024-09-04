package com.mohaberabi.linkedinclone.usermedida.presentation.viewmodel


sealed interface UserMediaActions {


    data class ConfirmUpload(val isCover: Boolean) : UserMediaActions
    data class ProfilePictureChanged(
        val bytes: ByteArray,
    ) : UserMediaActions
}