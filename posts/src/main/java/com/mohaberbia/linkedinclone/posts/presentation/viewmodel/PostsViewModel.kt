package com.mohaberbia.linkedinclone.posts.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.model.InAppNotificationBehaviour
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedinclone.core.remote_logging.domain.RemoteLogging
import com.mohaberabi.linkedinclone.core.remote_logging.domain.collect

import com.mohaberbia.linkedinclone.posts.usecase.GetPostsUseCase
import com.mohaberbia.linkedinclone.posts.usecase.ListenToPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    listenToPostsUseCase: ListenToPostsUseCase,
    private val getPostsUseCase: GetPostsUseCase,
    private val remoteLogger: RemoteLogging,
) : ViewModel() {
    private val _state = MutableStateFlow(PostsState())
    val state = _state.asStateFlow()


    init {
        getPosts()
        logStateChanges()
    }

    init {
        listenToPostsUseCase()
            .onStart {
                _state.update { it.copy(state = PostsStatus.Loading) }
            }.catch {
                _state.update {
                    it.copy(state = PostsStatus.Error)
                }
            }.onEach { posts ->
                updatePosts(posts)
            }.retry(retries = 3)
            .launchIn(viewModelScope)
    }


    fun onAction(action: PostsActions) {
        when (action) {
            PostsActions.LoadMore -> getPosts(
                lastDocId = _state.value.posts.lastOrNull()?.id
            )

            PostsActions.Refresh -> getPosts(refresh = true)
        }
    }


    private fun updatePosts(
        posts: List<PostModel>,
    ) {
        _state.update { state ->
            state.copy(
                state = PostsStatus.Populated,
                posts = posts,
            )
        }
    }


    private fun getPosts(
        lastDocId: String? = null,
        refresh: Boolean = false,
    ) {
        _state.update { it.copy(isRefreshing = refresh) }
        viewModelScope.launch {
            getPostsUseCase(lastDocId = lastDocId)
            _state.update { it.copy(isRefreshing = false) }
        }
    }


    private fun logStateChanges() {
        remoteLogger.collect(
            flow = state,
            scope = viewModelScope,
            action = { state ->
                logStateChange(
                    root = javaClass.simpleName,
                    stateName = state.javaClass.simpleName,
                    stateValue = state.state.name,
                )
            }
        )
    }
}



