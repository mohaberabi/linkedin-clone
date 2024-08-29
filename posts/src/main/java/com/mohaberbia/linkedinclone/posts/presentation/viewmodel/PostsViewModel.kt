package com.mohaberbia.linkedinclone.posts.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedin.core.domain.usecase.ListenToUserReactionsUseCase
import com.mohaberabi.linkedin.core.domain.usecase.ReactToPostUseCase
import com.mohaberabi.linkedin.core.domain.usecase.UndoReactToPostUseCase
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.presentation.ui.util.asUiText
import com.mohaberbia.linkedinclone.posts.usecase.ListenToPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PostsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val listenToPostsUseCase: ListenToPostsUseCase,
    private val reactToPostUseCase: ReactToPostUseCase,
    private val listenToUserReactionsUseCase: ListenToUserReactionsUseCase,
    private val undoReactToPostUseCase: UndoReactToPostUseCase,
) : ViewModel() {

    companion object {
        private const val POSTS_LIMIT_KEY = "postLimit"
        private const val DEFAULT_POSTS_LIMIT = 10
        private const val DEBOUNCE_DELAY = 300L

    }

    private val _state = MutableStateFlow(PostsState())
    val state = _state.asStateFlow()

    private val _events = Channel<PostsEvents>()
    val events = _events.receiveAsFlow()
    private var reactJob: Job? = null

    private var postsFlow = savedStateHandle.getStateFlow<Int?>(
        POSTS_LIMIT_KEY,
        DEFAULT_POSTS_LIMIT
    ).flatMapLatest { limit ->
        listenToPostsUseCase(limit = limit ?: DEFAULT_POSTS_LIMIT)
    }

    private val combinedPostsReactionsFlow = postsFlow.flatMapLatest { posts ->
        val postIds = posts.map { it.id }
        listenToUserReactionsUseCase(postIds)
            .map { reactions ->
                posts.map { post ->
                    post.copy(currentUserReaction = reactions[post.id])
                }
            }
    }


    init {
        combinedPostsReactionsFlow
            .onStart {
                if (_state.value.posts.isEmpty()) {
                    _state.update { it.copy(state = PostsStatus.Loading) }
                }
            }
            .catch {
                _state.update {
                    it.copy(state = PostsStatus.Error)
                }
            }
            .onEach { posts ->
                updatePosts(posts)
            }.retry(retries = 3)
            .launchIn(viewModelScope)

    }

    fun onAction(action: PostsActions) {
        when (action) {
            PostsActions.LoadMore -> loadMore()
            is PostsActions.ReactToPost -> handleReactionDebounced(action)
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

    private fun handleReactionDebounced(
        action: PostsActions.ReactToPost,
    ) {
        reactJob?.cancel()
        reactJob = viewModelScope.launch {
            delay(DEBOUNCE_DELAY)
            reactOrUndoReaction(action)
        }
    }

    private suspend fun reactOrUndoReaction(
        action: PostsActions.ReactToPost,
    ) {
        val sameReaction = action.reactionType == action.previousReactionType
        if (sameReaction) {
            undoReactToPost(
                postId = action.postId,
            )
        } else {
            reactToPost(
                postId = action.postId,
                reactionType = action.reactionType,
                previousReactionType = action.previousReactionType
            )
        }
    }

    private suspend fun undoReactToPost(
        postId: String,
    ) {
        undoReactToPostUseCase(
            postId = postId,
        )
            .onFailure { fail ->
                _events.send(PostsEvents.Error(fail.asUiText()))
            }
    }

    private suspend fun reactToPost(
        postId: String,
        reactionType: ReactionType,
        previousReactionType: ReactionType? = null,
    ) {
        val incrementedCount = if (previousReactionType == null) 1 else 0
        reactToPostUseCase(
            postId = postId,
            reactionType = reactionType,
            incrementCount = incrementedCount
        ).onFailure { fail ->
            _events.send(PostsEvents.Error(fail.asUiText()))
        }
    }

    private fun loadMore() {
        val lastPage = savedStateHandle[POSTS_LIMIT_KEY] ?: DEFAULT_POSTS_LIMIT
        savedStateHandle[POSTS_LIMIT_KEY] = lastPage + DEFAULT_POSTS_LIMIT
    }

}

