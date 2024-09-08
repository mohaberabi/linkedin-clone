package com.mohaberabi.linkedinclone.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.model.InAppNotificationBehaviour
import com.mohaberabi.linkedin.core.domain.model.UserMetaData
import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.linkedin.core.domain.usecase.in_app_noti.AddInAppNotificationUseCase
import com.mohaberabi.linkedin.core.domain.usecase.user.ListenToUserUseCase
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.linkedinclone.profile.domain.usecase.ViewSomeoneProfileUseCase
import com.mohaberabi.presentation.ui.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val listenToUserUseCase: ListenToUserUseCase,
    private val viewSomeoneProfileUseCase: ViewSomeoneProfileUseCase,
    private val addInAppNotificationUseCase: AddInAppNotificationUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()
    fun onAction(action: ProfileActions) {
        when (action) {
            is ProfileActions.LoadOtherUser -> {
                val uid = action.uid
                collectOtherUser(uid)
                notifyOtherOfProfileViewed(uid)
                viewOtherProfile(uid)
            }
        }
    }

    private fun collectOtherUser(uid: String) {
        listenToUserUseCase(uid = uid)
            .onStart { _state.update { it.copy(state = ProfileStatus.Loading) } }
            .onEach { user -> user?.let { userLoaded(user = it) } ?: handleError() }
            .catch { _state.update { it.copy(state = ProfileStatus.Error) } }
            .launchIn(viewModelScope)
    }


    private fun notifyOtherOfProfileViewed(receiverId: String) {
        viewModelScope.launch {
            val behaviour = InAppNotificationBehaviour.ViewProfile
            addInAppNotificationUseCase(
                behaviour = behaviour,
                receiverId = receiverId
            ).onSuccess { }
                .onFailure { }
        }
    }

    private fun viewOtherProfile(viewedUid: String) {
        viewModelScope.launch {
            viewSomeoneProfileUseCase(viewedUid = viewedUid)
                .onSuccess { }
                .onFailure { }
        }
    }

    private fun handleError() {
        _state.update {
            it.copy(
                state = ProfileStatus.Error,
                error = UiText.userDeleted
            )
        }
    }

    private fun userLoaded(
        user: UserModel,
    ) {
        _state.update {
            it.copy(
                user = user,
                state = ProfileStatus.Done,
            )
        }
    }

}