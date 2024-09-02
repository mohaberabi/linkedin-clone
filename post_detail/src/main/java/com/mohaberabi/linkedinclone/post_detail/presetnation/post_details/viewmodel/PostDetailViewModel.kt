package com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedin.core.domain.usecase.reaction.PostReactionHandler
import com.mohaberabi.linkedin.core.domain.usecase.reaction.ReactToPostUseCase
import com.mohaberabi.linkedin.core.domain.usecase.reaction.UndoReactToPostUseCase
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.linkedinclone.post_detail.domain.usecase.CommentOnPostUseCase
import com.mohaberabi.linkedinclone.post_detail.domain.usecase.GetPostCommentsUseCase
import com.mohaberabi.linkedinclone.post_detail.domain.usecase.ListenToPostDetailUseCase
import com.mohaberabi.presentation.ui.util.UiText
import com.mohaberabi.presentation.ui.util.asUiText
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val commentOnPostUseCase: CommentOnPostUseCase,
    private val reactToPostUseCase: ReactToPostUseCase,
    private val getPostCommentsUseCase: GetPostCommentsUseCase,
    private val undoReactToPostUseCase: UndoReactToPostUseCase,
    private val postReactionHandler: PostReactionHandler,
    private val listenToPostDetailUseCase: ListenToPostDetailUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private var reactJob: Job? = null

    companion object {
        private const val DEBOUNCE_DELAY = 300L

        private const val POST_NAV_ARG = "postId"
    }

    private val postId = savedStateHandle.get<String>(POST_NAV_ARG)

    private val _state = MutableStateFlow(PostDetailState())
    val state = _state.asStateFlow()

    private val _event = Channel<PostDetailsEvents>()
    val event = _event.receiveAsFlow()

    init {
        postId?.let { id ->
            listenToPostDetailUseCase(id)
                .onStart { _state.update { it.copy(state = PostDetailStatus.Loading) } }
                .catch { _state.update { it.copy(state = PostDetailStatus.Error) } }
                .onEach { detail ->
                    _state.update {
                        it.copy(
                            topPostReactions = detail.topReactions,
                            postComments = detail.topComments,
                            currentPost = detail.post,
                            state = PostDetailStatus.Populated
                        )
                    }
                }.launchIn(viewModelScope)
        }
    }

    fun onAction(action: PostDetailActions) {
        when (action) {
            is PostDetailActions.CommentChanged -> commentChanged(action.comment)
            PostDetailActions.LoadMoreComments -> loadMoreComments()
            is PostDetailActions.ReactOnPost -> handleReactionDebounced(action)
            PostDetailActions.SubmitComment -> commentOnPost()
        }
    }


    private fun loadMoreComments() {
        val stateVal = _state.value
        viewModelScope.launch {
            getPostCommentsUseCase(
                postId = postId!!,
                lastDocId = stateVal.postComments.lastOrNull()?.id
            ).onSuccess { newComments ->
                _state.update {
                    it.copy(
                        postComments = it.postComments + newComments
                    )
                }
            }
        }

    }

    private fun handleReactionDebounced(
        action: PostDetailActions.ReactOnPost,
    ) {
        reactJob?.cancel()
        reactJob = viewModelScope.launch {
            delay(DEBOUNCE_DELAY)
            postReactionHandler(
                postId = postId!!,
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
        sendErrorEvent(fail.asUiText())
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
        sendErrorEvent(fail.asUiText())
    }

    private fun commentChanged(
        value: String,
    ) = _state.update { it.copy(postComment = value) }


    private fun commentOnPost() {
        viewModelScope.launch {
            _state.update { it.copy(commentLoading = true) }
            commentOnPostUseCase(
                postId = postId!!,
                comment = _state.value.postComment
            ).onFailure { fail ->
                sendErrorEvent(fail.asUiText())
            }
            _state.update { it.copy(commentLoading = false) }
        }
    }


    private suspend fun sendErrorEvent(fail: UiText) {
        _event.send(PostDetailsEvents.Error(fail))
    }


}