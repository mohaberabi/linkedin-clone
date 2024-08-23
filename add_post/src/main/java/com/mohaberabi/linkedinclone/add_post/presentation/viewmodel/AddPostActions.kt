package com.mohaberabi.linkedinclone.add_post.presentation.viewmodel


sealed interface AddPostActions {
    data object PickPostImage : AddPostActions
    data class SavePostImage(val imgBytes: ByteArray) : AddPostActions
}