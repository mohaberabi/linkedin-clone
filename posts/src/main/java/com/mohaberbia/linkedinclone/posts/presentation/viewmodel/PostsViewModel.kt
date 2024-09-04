package com.mohaberbia.linkedinclone.posts.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedin.core.domain.usecase.reaction.PostReactionHandler
import com.mohaberabi.linkedin.core.domain.usecase.reaction.ReactToPostUseCase
import com.mohaberabi.linkedin.core.domain.usecase.reaction.UndoReactToPostUseCase
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.presentation.ui.util.asUiText
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
    private val reactToPostUseCase: ReactToPostUseCase,
    private val undoReactToPostUseCase: UndoReactToPostUseCase,
    private val getPostsUseCase: GetPostsUseCase,
    private val postReactionHandler: PostReactionHandler,
) : ViewModel() {

    companion object {
        private const val DEBOUNCE_DELAY = 300L
    }

    private val _state = MutableStateFlow(PostsState())
    val state = _state.asStateFlow()

    private val _events = Channel<PostsEvents>()
    val events = _events.receiveAsFlow()
    private var reactJob: Job? = null


    init {
        getPosts()
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

            is PostsActions.ReactToPost -> handleReactionDebounced(action)
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

    private fun handleReactionDebounced(
        action: PostsActions.ReactToPost,
    ) {
        reactJob?.cancel()
        reactJob = viewModelScope.launch {
            delay(DEBOUNCE_DELAY)
            postReactionHandler(
                postId = action.postId,
                previousReactionType = action.previousReactionType,
                reactionType = action.reactionType,
                onReactToPost = { postId, reaction, incrementedCount ->
                    reactToPost(
                        postId = postId,
                        reactionType = reaction,
                        incrementedCount = incrementedCount,
                    )
                },
                onUndoReaction = { postId ->
                    undoReactToPost(postId)
                }
            )
        }
    }


    private suspend fun undoReactToPost(
        postId: String,
    ) = undoReactToPostUseCase(
        postId = postId,
    ).onFailure { fail ->
        _events.send(PostsEvents.Error(fail.asUiText()))
    }

    private suspend fun reactToPost(
        postId: String,
        reactionType: ReactionType,
        incrementedCount: Int,
    ) = reactToPostUseCase(
        postId = postId,
        reactionType = reactionType,
        incrementCount = incrementedCount
    ).onFailure { fail ->
        _events.send(PostsEvents.Error(fail.asUiText()))
    }


    private fun getPosts(
        lastDocId: String? = null,
        refresh: Boolean = false,
    ) {
        _state.update { it.copy(isRefreshing = refresh) }
        viewModelScope.launch {
            getPostsUseCase(
                lastDocId = lastDocId
            )
            _state.update { it.copy(isRefreshing = false) }
        }
    }
}



