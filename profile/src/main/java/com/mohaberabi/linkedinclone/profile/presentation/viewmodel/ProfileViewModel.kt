package com.mohaberabi.linkedinclone.profile.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.linkedin.core.domain.usecase.user.GetUserUseCase
import com.mohaberabi.linkedin.core.domain.usecase.user.ListenToCurrentUserUseCase
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.presentation.ui.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {


    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()


    fun onAction(action: ProfileActions) {
        when (action) {
            is ProfileActions.GetUser -> getOtherUser(action.uid)
        }
    }

    private fun getOtherUser(uid: String) {
        _state.update { it.copy(state = ProfileStatus.Loading) }
        viewModelScope.launch {
            getUserUseCase(uid)
                .onFailure {
                    _state.update {
                        it.copy(
                            state = ProfileStatus.Error,
                            error = UiText.userDeleted
                        )
                    }
                }
                .onSuccess { otherUser ->
                    _state.update {
                        it.copy(
                            user = otherUser,
                            canEdit = false,
                            state = ProfileStatus.Populated,
                        )
                    }
                }
        }

    }


}