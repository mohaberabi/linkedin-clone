package com.mohaberabi.linkedinclone.user_metadata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.model.UserMetaData
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedinclone.core.remote_logging.domain.model.LogInfo
import com.mohaberabi.linkedinclone.core.remote_logging.domain.usecase.RemoteLoggingUseCases
import com.mohaberabi.linkedinclone.user_metadata.usecase.ListenToUserMetaDataUseCase
import com.mohaberabi.linkedinclone.user_metadata.usecase.MarkAllNotificationsReadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserMetaDataViewModel @Inject constructor(
    private val listenToUserMetaDataUseCase: ListenToUserMetaDataUseCase,
    private val markAllNotificationsReadUseCase: MarkAllNotificationsReadUseCase,
    private val remoteLogger: RemoteLoggingUseCases,
) : ViewModel() {
    val state = listenToUserMetaDataUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            UserMetaData()
        )


    fun markAllNotificationsRead() {
        viewModelScope.launch {
            markAllNotificationsReadUseCase()
                .onFailure { fail ->
                    remoteLogger.log(LogInfo.Info(message = fail.toString()))
                    remoteLogger.recordException(fail.cause)
                }
        }
    }
}