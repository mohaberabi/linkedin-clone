package com.mohaberabi.linkedinclone.post_detail.presetnation.post_reactions.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.linkedinclone.post_detail.domain.usecase.GetPostReactionsUseCase
import com.mohaberabi.presentation.ui.util.UiText
import com.mohaberabi.presentation.ui.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostReactionsViewModel @Inject constructor(
    private val getPostReactionsUseCase: GetPostReactionsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var getReactionsJob: Job? = null
    private val postId = savedStateHandle.get<String>("postId")

    private val _state = MutableStateFlow(PostReactionsState())
    val state = _state.asStateFlow()


    init {

        getReactions(
            reactionType = null,
        )
    }

    fun onAction(action: PostReactionsActions) {
        when (action) {
            PostReactionsActions.LoadMore -> loadMore()
            is PostReactionsActions.ReactionTypeChanged -> getReactions(
                reactionType = action.type,
            )
        }
    }


    private fun getReactions(
        reactionType: ReactionType?
    ) {
        getReactionsJob?.cancel()
        getReactionsJob = viewModelScope.launch {
            _state.update {
                it.copy(
                    state = PostReactionsStatus.Loading,
                    reactionType = reactionType,
                    reactions = emptyList()
                )
            }
            getPostReactionsUseCase(
                postId = postId!!,
                reactionType = reactionType?.name
            ).onFailure { fail ->
                emitError(fail.asUiText())
            }.onSuccess { reactions ->
                _state.update {
                    it.copy(
                        reactions = reactions,
                        state = PostReactionsStatus.Populated
                    )
                }
            }
        }

    }

    private fun emitError(
        error: UiText? = null,
    ) {
        _state.update {
            it.copy(
                error = error ?: UiText.unknown,
                state = PostReactionsStatus.Error
            )
        }
    }

    private fun loadMore() {
        val stateVal = _state.value
        viewModelScope.launch {
            getPostReactionsUseCase(
                postId = postId!!,
                reactionType = stateVal.reactionType?.name,
                lastDocId = stateVal.reactions.lastOrNull()?.reactorId
            ).onSuccess { reactions ->
                _state.update { it.copy(reactions = it.reactions + reactions) }
            }
        }
    }
}