package com.mohaberabi.linkedinclone.add_post.presentation.viewmodel


data class AddPostState(
    val postImgByteArray: ByteArray = byteArrayOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AddPostState

        return postImgByteArray.contentEquals(other.postImgByteArray)
    }

    override fun hashCode(): Int {
        return postImgByteArray.contentHashCode()
    }
}