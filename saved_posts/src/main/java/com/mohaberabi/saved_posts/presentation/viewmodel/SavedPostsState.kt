package com.mohaberabi.saved_posts.presentation.viewmodel

import com.mohaberabi.linkedin.core.domain.model.PostModel

sealed interface SavedPostsState {


    data object Loading : SavedPostsState

    data object Error : SavedPostsState

    data class Populated(val savedPosts: List<PostModel>) : SavedPostsState
}