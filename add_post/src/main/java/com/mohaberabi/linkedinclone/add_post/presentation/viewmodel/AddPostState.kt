package com.mohaberabi.linkedinclone.add_post.presentation.viewmodel


data class AddPostState(
    val postImgByteArray: ByteArray = byteArrayOf(),
    val postData: String = "",
    val loading: Boolean = false,
    val userImg: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AddPostState

        if (!postImgByteArray.contentEquals(other.postImgByteArray)) return false
        if (postData != other.postData) return false
        if (loading != other.loading) return false

        return true
    }

    override fun hashCode(): Int {
        var result = postImgByteArray.contentHashCode()
        result = 31 * result + postData.hashCode()
        result = 31 * result + loading.hashCode()
        return result
    }


    val canAddPost: Boolean
        get() = postImgByteArray.isNotEmpty() || postData.trim().isNotEmpty()
}