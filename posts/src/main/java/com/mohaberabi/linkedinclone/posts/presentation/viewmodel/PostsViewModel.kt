package com.mohaberabi.linkedinclone.posts.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.linkedinclone.posts.domain.usecase.GetPostsUseCase
import com.mohaberabi.presentation.ui.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(PostsState())
    val state = _state.asStateFlow()

    init {

        getPosts()
    }

    fun onAction(action: PostsActions) {
        when (action) {
            PostsActions.LoadMore -> loadMore()
        }
    }

    private fun loadMore() {
        viewModelScope.launch {
            delay(3000)
            getPostsUseCase()
                .onFailure { fail ->
                }
                .onSuccess { posts ->
                    _state.update {
                        it.copy(
                            posts = it.posts + posts
                        )
                    }
                }
        }
    }

    private fun getPosts() {
        _state.update { it.copy(state = PostsStatus.Loading) }
        viewModelScope.launch {
            delay(3000)
            getPostsUseCase()
                .onFailure { fail ->
                    _state.update {
                        it.copy(
                            state = PostsStatus.Error,
                            error = fail.asUiText()
                        )
                    }
                }
                .onSuccess { posts ->
                    _state.update {
                        it.copy(
                            state = PostsStatus.Populated,
                            posts = posts
                        )
                    }
                }
        }
    }
}