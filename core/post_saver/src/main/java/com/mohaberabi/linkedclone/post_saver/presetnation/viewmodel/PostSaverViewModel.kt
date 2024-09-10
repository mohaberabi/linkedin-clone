package com.mohaberabi.linkedclone.post_saver.presetnation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.LogEventUseCase
import com.mohaberabi.linkedclone.post_saver.domain.logPostSaved
import com.mohaberabi.linkedclone.post_saver.domain.usecase.SavePostUseCase
import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.linkedinclone.core.remote_logging.domain.model.LogInfo
import com.mohaberabi.linkedinclone.core.remote_logging.domain.usecase.RemoteLogUseCase
import com.mohaberabi.linkedinclone.core.remote_logging.domain.usecase.RemoteLoggingUseCases
import com.mohaberabi.presentation.ui.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostSaverViewModel @Inject constructor(
    private val savePostUseCase: SavePostUseCase,
    private val remoteLoggingUseCases: RemoteLoggingUseCases,
    private val logEventUseCase: LogEventUseCase,
) : ViewModel() {


    private val _event = Channel<PostSaverEvents>()
    val event = _event.receiveAsFlow()


    fun onAction(action: PostSaverActions) {
        when (action) {
            is PostSaverActions.SavePost -> savePost(action.id)
        }
    }

    private fun savePost(
        id: String,
    ) {
        viewModelScope.launch {
            savePostUseCase(id)
                .onFailure { fail ->
                    _event.send(PostSaverEvents.Error(fail.asUiText()))
                    logRemote(fail)
                }.onSuccess {
                    _event.send(PostSaverEvents.PostSaved)
                    logEventUseCase.logPostSaved(id)
                }
        }
    }

    private fun logRemote(fail: ErrorModel) {
        remoteLoggingUseCases.log(LogInfo.Error(fail.toString()))
        remoteLoggingUseCases.recordException(fail.cause)
    }
}