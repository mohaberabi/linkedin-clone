package com.mohaberabi.linkedinclone.usermedida.avatar.viewmodel


sealed interface AvatarActions {


    data object ConfirmUpload : AvatarActions
    data class AvatarChanged(val bytes: ByteArray) : AvatarActions {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as AvatarChanged

            return bytes.contentEquals(other.bytes)
        }

        override fun hashCode(): Int {
            return bytes.contentHashCode()
        }
    }
}