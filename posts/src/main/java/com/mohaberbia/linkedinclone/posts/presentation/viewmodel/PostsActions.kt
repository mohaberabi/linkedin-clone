package com.mohaberbia.linkedinclone.posts.presentation.viewmodel

sealed interface PostsActions {
    data object LoadMore : PostsActions
    data object Refresh : PostsActions

}