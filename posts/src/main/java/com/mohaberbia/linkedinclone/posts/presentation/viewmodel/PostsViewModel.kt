package com.mohaberbia.linkedinclone.posts.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.linkedinclone.core.remote_logging.RemoteLogger
import com.mohaberbia.linkedinclone.posts.usecase.GetPostsUseCase
import com.mohaberabi.presentation.ui.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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

    private var loadMoreJob: Job? = null

    init {

        getPosts()
    }

    fun onAction(action: PostsActions) {
        when (action) {
            PostsActions.LoadMore -> loadMore()
            PostsActions.Refresh -> getPosts()
        }
    }

    private fun loadMore() {
        loadMoreJob?.cancel()
        _state.value.logRemotely()

        loadMoreJob = viewModelScope.launch {
            getPostsUseCase(
                lastDocId = _state.value.posts.lastOrNull()?.id,
            )
                .onSuccess { newPosts ->
                    _state.update {
                        it.copy(
                            posts = it.posts + newPosts
                        )
                    }
                    _state.value.logRemotely()
                }
        }
    }

    private fun getPosts() {
        _state.value.logRemotely()
        _state.update { it.copy(state = PostsStatus.Loading) }
        viewModelScope.launch {
            getPostsUseCase()
                .onFailure { fail ->

                    _state.update {
                        it.copy(
                            state = PostsStatus.Error,
                            error = fail.asUiText()
                        )
                    }
                    _state.value.logRemotely()

                }
                .onSuccess { posts ->
                    _state.update {
                        it.copy(
                            state = PostsStatus.Populated,
                            posts = posts
                        )
                    }
                    _state.value.logRemotely()
                }

        }
    }
}