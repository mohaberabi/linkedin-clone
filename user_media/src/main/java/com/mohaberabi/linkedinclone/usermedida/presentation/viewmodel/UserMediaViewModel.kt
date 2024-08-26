package com.mohaberabi.linkedinclone.usermedida.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.linkedinclone.usermedida.domain.usecase.UpdateImgUseCase
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
class UserMediaViewModel @Inject constructor(
    private val updateImgUseCase: UpdateImgUseCase,
) : ViewModel() {


    private val _state = MutableStateFlow(UserMediaState())
    val state = _state.asStateFlow()
    private val _events = Channel<UserMediaEvents>()
    val events = _events.receiveAsFlow()
    fun onAction(action: UserMediaActions) {
        when (action) {
            is UserMediaActions.ProfilePictureChanged -> pictureChanged(action.bytes)
            UserMediaActions.ConfirmUpload -> uploadImage()
        }
    }

    private fun pictureChanged(bytes: ByteArray) {
        _state.update {
            it.copy(avatar = _state.value.avatar.copy(bytes = bytes))
        }
    }

    private fun uploadImage() {
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            updateImgUseCase(_state.value.avatar)
                .onSuccess {
                    _events.send(UserMediaEvents.Uploaded)
                }
                .onFailure { fail ->
                    _events.send(UserMediaEvents.Error(fail.asUiText()))
                }
            _state.update { it.copy(loading = false) }

        }
    }

}