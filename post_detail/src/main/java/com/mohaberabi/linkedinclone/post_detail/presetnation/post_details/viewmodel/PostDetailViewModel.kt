package com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.model.InAppNotificationBehaviour
import com.mohaberabi.linkedin.core.domain.usecase.in_app_noti.AddInAppNotificationUseCase
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.linkedinclone.post_detail.domain.usecase.CommentOnPostUseCase
import com.mohaberabi.linkedinclone.post_detail.domain.usecase.GetPostCommentsUseCase
import com.mohaberabi.linkedinclone.post_detail.domain.usecase.ListenToPostDetailUseCase
import com.mohaberabi.linkedinclone.post_detail.domain.usecase.analytics.LogPostOpenedUseCase
import com.mohaberabi.linkedinclone.post_detail.domain.usecase.analytics.PostAnaylticsUseCases
import com.mohaberabi.presentation.ui.util.UiText
import com.mohaberabi.presentation.ui.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
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
    private val getPostCommentsUseCase: GetPostCommentsUseCase,
    private val listenToPostDetailUseCase: ListenToPostDetailUseCase,
    private val addInAppNotificationUseCase: AddInAppNotificationUseCase,
    private val postAnaylticsUseCases: PostAnaylticsUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
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
                        postAnaylticsUseCases.postOpened(detail.post!!.id)
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
            PostDetailActions.SubmitComment -> commentOnPost()
            is PostDetailActions.CurrentUIdChanged -> currentUserUidChanged(action.uid)
        }
    }

    private fun currentUserUidChanged(uid: String) = _state.update { it.copy(currentUserUid = uid) }

    private fun loadMoreComments() {
        val stateVal = _state.value
        viewModelScope.launch {
            getPostCommentsUseCase(
                postId = postId!!,
                lastDocId = stateVal.postComments.lastOrNull()?.id
            ).onSuccess { newComments ->
                val id = _state.value.currentPost!!.id
                postAnaylticsUseCases.postHasCommentInterest(id)
                _state.update {
                    it.copy(
                        postComments = it.postComments + newComments
                    )
                }
            }
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
            ).onFailure { fail ->
                sendErrorEvent(fail.asUiText())

            }.onSuccess {
                val id = _state.value.currentPost!!.id
                addInAppNotificationOnComment()
                postAnaylticsUseCases.postGotComment(id)
            }
            _state.update { it.copy(commentLoading = false) }
        }
    }


    private suspend fun sendErrorEvent(fail: UiText) {
        _event.send(PostDetailsEvents.Error(fail))
    }

    private fun addInAppNotificationOnComment() {
        val comment = _state.value.postComment
        val post = _state.value.currentPost!!
        val uid = _state.value.currentUserUid
        if (post.issuerUid != uid) {
            viewModelScope.launch {
                val behaviour = InAppNotificationBehaviour.Comment(
                    comment = comment,
                    postId = post.id,
                )
                addInAppNotificationUseCase(
                    behaviour = behaviour,
                    receiverId = post.issuerUid
                )
            }
        }
    }

}