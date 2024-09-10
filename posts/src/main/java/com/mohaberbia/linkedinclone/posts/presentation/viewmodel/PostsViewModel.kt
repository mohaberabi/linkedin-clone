package com.mohaberbia.linkedinclone.posts.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.LogEventUseCase
import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedinclone.core.remote_logging.domain.model.LogInfo
import com.mohaberabi.linkedinclone.core.remote_logging.domain.model.log
import com.mohaberabi.linkedinclone.core.remote_logging.domain.model.logWithError
import com.mohaberabi.linkedinclone.core.remote_logging.domain.usecase.RemoteLoggingUseCases
import com.mohaberbia.linkedinclone.posts.domain.logPostsGoInterest

import com.mohaberbia.linkedinclone.posts.domain.usecase.GetPostsUseCase
import com.mohaberbia.linkedinclone.posts.domain.usecase.ListenToPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    listenToPostsUseCase: ListenToPostsUseCase,
    private val getPostsUseCase: GetPostsUseCase,
    private val logEventUseCase: LogEventUseCase,
    private val remoteLoggingUseCases: RemoteLoggingUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(PostsState())
    val state = _state.asStateFlow()
        .logWithError(
            onError = {
                remoteLoggingUseCases.recordException(it)
            },
            onDone = {
                remoteLoggingUseCases.log(it)
            },
            map = { it.state.name }
        )

    init {
        getPosts()
    }

    init {
        listenToPostsUseCase()
            .onStart {
                _state.update {
                    it.copy(state = PostsStatus.Loading)
                }
            }.catch { error ->
                remoteLoggingUseCases.recordException(error)
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
            PostsActions.LoadMore -> {
                getPosts(_state.value.posts.lastOrNull()?.id)
                logEventUseCase.logPostsGoInterest()
            }

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
                .onFailure { logRemote(it) }
            _state.update { it.copy(isRefreshing = false) }
        }
    }

    private fun logRemote(fail: ErrorModel) {
        remoteLoggingUseCases.log(LogInfo.Error(fail.toString()))
        remoteLoggingUseCases.recordException(fail.cause)
    }
}



