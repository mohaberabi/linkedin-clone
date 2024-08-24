package com.mohaberabi.linkedinclone.profile.presentation.viewmodel

import androidx.core.R
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.linkedin.core.domain.usecase.GetUserUseCase
import com.mohaberabi.linkedin.core.domain.usecase.ListenToCurrentUserUseCase
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
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val listenToUserUseCase: ListenToCurrentUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {


    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()
    private val uid = savedStateHandle.get<String>("uid")


    init {
        handleUser()
    }


    private fun handleUser() {
        listenToUserUseCase()
            .onStart {
                _state.update { it.copy(state = ProfileStatus.Loading) }
            }
            .catch {
                handleFailure()
            }
            .onEach { user ->
                if (uid == null) {
                    handleUser(user)
                } else {
                    if (uid == user?.uid) {
                        handleUser(user)
                    } else {
                        getOtherUser(uid)
                    }
                }
            }.launchIn(viewModelScope)
    }


    private suspend fun getOtherUser(uid: String) {
        getUserUseCase(uid)
            .onFailure { handleFailure() }
            .onSuccess { otherUser -> handleUser(otherUser, false) }
    }

    private fun handleFailure() {
        _state.update {
            it.copy(
                state = ProfileStatus.Error,
                error = UiText.userDeleted
            )
        }
    }

    private fun handleUser(
        user: UserModel?,
        canEdit: Boolean = true
    ) {
        val status = if (user == null) {
            ProfileStatus.Error
        } else {
            ProfileStatus.Populated
        }
        _state.update {
            it.copy(
                user = user,
                canEdit = canEdit,
                state = status,
            )
        }
    }
}