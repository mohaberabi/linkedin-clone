package com.mohaberabi.linkedinclone.post_reactions.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.InAppNotificationBehaviour
import com.mohaberabi.linkedin.core.domain.model.annotations.MentorNote
import com.mohaberabi.linkedin.core.domain.usecase.in_app_noti.AddInAppNotificationUseCase
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.linkedinclone.core.remote_logging.domain.model.LogInfo
import com.mohaberabi.linkedinclone.core.remote_logging.domain.usecase.RemoteLoggingUseCases
import com.mohaberabi.linkedinclone.post_reactions.domain.usecase.ReactToPostUseCase
import com.mohaberabi.linkedinclone.post_reactions.domain.usecase.UndoReactToPostUseCase
import com.mohaberabi.presentation.ui.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log


@HiltViewModel
class ReactToPostViewModel @Inject constructor(
    private val reactToPostUseCase: ReactToPostUseCase,
    private val undoReactToPostUseCase: UndoReactToPostUseCase,
    private val addInAppNotificationUseCase: AddInAppNotificationUseCase,
    private val remoteLoggingUseCases: RemoteLoggingUseCases,
) : ViewModel() {

    companion object {
        private const val DEBOUNCE_DELAY = 300L
    }

    private val _events = Channel<ReactToPostEvents>()
    val events = _events.receiveAsFlow()
    private var reactJob: Job? = null


    fun onAction(action: ReactToPostActions) {
        when (action) {
            is ReactToPostActions.ReactToPost -> handleReactionDebounced(action.params)
        }
    }

    private fun handleReactionDebounced(
        params: PostReactionParams
    ) {
        reactJob?.cancel()
        reactJob = viewModelScope.launch {
            delay(DEBOUNCE_DELAY)
            val sameReaction = params.reactionType == params.previousReactionType
            if (sameReaction) {
                undoReactToPost(params.postId)
            } else {
                val incrementedCount = if (params.previousReactionType == null) 1 else 0
                reactToPost(
                    incrementedCount = incrementedCount,
                    params = params
                )
            }

        }
    }


    private suspend fun undoReactToPost(
        postId: String,
    ) = undoReactToPostUseCase(
        postId = postId,
    ).onFailure { fail ->
        _events.send(ReactToPostEvents.Error(fail.asUiText()))
        logRemote(fail)
    }

    private suspend fun reactToPost(
        incrementedCount: Int,
        params: PostReactionParams,
    ) = reactToPostUseCase(
        postId = params.postId,
        reactionType = params.reactionType,
        incrementCount = incrementedCount
    ).onFailure { fail ->
        _events.send(ReactToPostEvents.Error(fail.asUiText()))
        logRemote(fail)
    }.onSuccess {
        addInAppNotificationsOnReaction(params = params)
    }


    private fun addInAppNotificationsOnReaction(
        params: PostReactionParams,
    ) {
        val behaviour = InAppNotificationBehaviour.Reaction(
            reactionType = params.reactionType,
            postId = params.postId,
            postHeader = params.postHeader
        )
        viewModelScope.launch {
            addInAppNotificationUseCase(
                behaviour = behaviour,
                receiverId = params.postOwnerId,
            ).onFailure { fail ->
                logRemote(fail)
            }
        }
    }

    private fun logRemote(fail: ErrorModel) {
        remoteLoggingUseCases.log(LogInfo.Error(fail.toString()))
        remoteLoggingUseCases.recordException(fail.cause)
    }
}