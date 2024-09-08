package com.mohaberabi.linkedinclone.in_app_notifications.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.linkedinclone.in_app_notifications.domain.usecase.GetInAppNotificationsUseCase
import com.mohaberabi.presentation.ui.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class InAppNotificationsViewModel @Inject constructor(
    private val getInAppNotificationsUseCase: GetInAppNotificationsUseCase,
) : ViewModel() {


    private val _state = MutableStateFlow(InAppNotificationsState())
    val state = _state.asStateFlow()


    init {
        getInAppNotifications()
    }

    fun onAction(action: AppNotificationsActions) {
        when (action) {
            AppNotificationsActions.LoadMore -> loadMoreNotifications()
        }

    }

    private fun getInAppNotifications() {
        _state.update { it.copy(state = InAppNotiStatus.Loading) }
        viewModelScope.launch {
            getInAppNotificationsUseCase()
                .onSuccess { notis ->
                    _state.update {
                        it.copy(
                            state = InAppNotiStatus.Populated,
                            notifications = notis
                        )
                    }
                }
                .onFailure { fail ->
                    _state.update {
                        it.copy(
                            state = InAppNotiStatus.Error,
                            error = fail.asUiText()
                        )
                    }
                }
        }
    }


    private fun loadMoreNotifications() {
        viewModelScope.launch {
            val lastDocId = _state.value.notifications.lastOrNull()?.id
            getInAppNotificationsUseCase(lastElementId = lastDocId)
                .onSuccess { notis ->
                    _state.update {
                        it.copy(
                            notifications = it.notifications + notis
                        )
                    }
                }
        }
    }

}