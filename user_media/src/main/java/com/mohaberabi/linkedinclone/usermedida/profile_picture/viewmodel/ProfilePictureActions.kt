package com.mohaberabi.linkedinclone.usermedida.profile_picture.viewmodel


sealed interface ProfilePictureActions {


    data object ConfirmUpload : ProfilePictureActions
    data class ProfilePictureChanged(
        val bytes: ByteArray,
    ) : ProfilePictureActions {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as ProfilePictureChanged
            return bytes.contentEquals(other.bytes)
        }

        override fun hashCode(): Int {
            return bytes.contentHashCode()
        }
    }
}