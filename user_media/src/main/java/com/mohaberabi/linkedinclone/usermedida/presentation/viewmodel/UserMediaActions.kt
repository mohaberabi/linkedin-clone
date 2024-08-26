package com.mohaberabi.linkedinclone.usermedida.presentation.viewmodel


sealed interface UserMediaActions {


    data object ConfirmUpload : UserMediaActions
    data class ProfilePictureChanged(
        val bytes: ByteArray,
    ) : UserMediaActions {
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