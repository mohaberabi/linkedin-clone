package com.mohaberabi.linkedinclone.usermedida.avatar.viewmodel


sealed interface AvatarActions {


    data object ConfirmUpload : AvatarActions
    data class AvatarChanged(val bytes: ByteArray) : AvatarActions
}