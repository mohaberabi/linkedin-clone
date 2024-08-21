package com.mohaberabi.linkedinclone.posts.presentation.viewmodel

sealed interface PostsActions {
    data object LoadMore : PostsActions
}