package com.mohaberabi.linkedinclone.add_post.presentation.viewmodel


sealed interface AddPostActions {
    data object PickPostImage : AddPostActions
    data class SavePostImage(val imgBytes: ByteArray) : AddPostActions

    data class PostDataChanged(val data: String) : AddPostActions


    data object SubmitPost : AddPostActions

}