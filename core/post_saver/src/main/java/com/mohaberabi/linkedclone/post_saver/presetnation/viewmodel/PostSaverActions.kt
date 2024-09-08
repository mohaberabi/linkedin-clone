package com.mohaberabi.linkedclone.post_saver.presetnation.viewmodel

sealed interface PostSaverActions {


    data class SavePost(val id: String) : PostSaverActions
}