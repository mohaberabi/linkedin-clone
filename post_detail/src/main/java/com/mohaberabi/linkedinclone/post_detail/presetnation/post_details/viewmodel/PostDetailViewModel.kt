package com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.model.PostCommentModel
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedin.core.domain.usecase.ReactToPostUseCase
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.linkedinclone.post_detail.domain.usecase.CommentOnPostUseCase
import com.mohaberabi.linkedinclone.post_detail.domain.usecase.GetPostCommentsUseCase
import com.mohaberabi.linkedinclone.post_detail.domain.usecase.GetPostDetailUseCase
import com.mohaberabi.presentation.ui.util.UiText
import com.mohaberabi.presentation.ui.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val getPostDetailUseCase: GetPostDetailUseCase,
    private val commentOnPostUseCase: CommentOnPostUseCase,
    private val reactToPostUseCase: ReactToPostUseCase,
    private val getPostCommentsUseCase: GetPostCommentsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {


    private val postId = savedStateHandle.get<String>("postId")

    private val _state = MutableStateFlow(PostDetailState())
    val state = _state.asStateFlow()

    private val _event = Channel<PostDetailsEvents>()
    val event = _event.receiveAsFlow()

    init {
        getPostDetails(postId!!)
    }

    fun onAction(action: PostDetailActions) {
        when (action) {
            is PostDetailActions.CommentChanged -> commentChanged(action.comment)
            PostDetailActions.LoadMoreComments -> loadMoreComments()
            is PostDetailActions.ReactOnPost -> reactToPost(action.reactionType)
            PostDetailActions.SubmitComment -> commentOnPost()
        }
    }


    private fun getPostDetails(
        postId: String,
    ) {
        _state.update { it.copy(state = PostDetailStatus.Loading) }
        viewModelScope.launch {
            getPostDetailUseCase(
                postId = postId,
            ).onFailure { fail ->
                _state.update {
                    it.copy(
                        state = PostDetailStatus.Error,
                        error = fail.asUiText()
                    )
                }
            }
                .onSuccess { postDetails ->
                    _state.update {
                        it.copy(
                            state = PostDetailStatus.Populated,
                            currentPost = postDetails.post,
                            topPostReactions = postDetails.topReactions,
                            postComments = postDetails.topComments
                        )
                    }
                }
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

    private fun reactToPost(
        reactionType: ReactionType,
    ) {
        viewModelScope.launch {


        }
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
            )
                .onFailure { fail ->
                    sendErrorEvent(fail.asUiText())
                }
                .onSuccess { comment ->
                    onCommentDone(comment)
                }
            _state.update { it.copy(commentLoading = false) }
        }
    }

    private suspend fun sendErrorEvent(fail: UiText) {
        _event.send(PostDetailsEvents.Error(fail))
    }

    private fun onCommentDone(
        comment: PostCommentModel,
    ) {
        _state.update {
            it.copy(
                postComment = "",
                currentPost = it.currentPost?.copy(
                    commentsCount = it.currentPost.commentsCount + 1,
                ),
                postComments = listOf(comment) + it.postComments
            )
        }
    }
}