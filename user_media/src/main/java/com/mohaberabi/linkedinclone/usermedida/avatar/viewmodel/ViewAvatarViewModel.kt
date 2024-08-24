package com.mohaberabi.linkedinclone.usermedida.avatar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.linkedinclone.usermedida.usecase.UpdateImgUseCase
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
class ViewAvatarViewModel @Inject constructor(
    private val updateImgUseCase: UpdateImgUseCase,
) : ViewModel() {


    private val _state = MutableStateFlow(AvatarState())
    val state = _state.asStateFlow()


    private val _events = Channel<AvatarEvents>()
    val events = _events.receiveAsFlow()


    fun onAction(action: AvatarActions) {
        when (action) {
            is AvatarActions.AvatarChanged -> _state.update {
                it.copy(avatar = _state.value.avatar)
            }

            AvatarActions.ConfirmUpload -> uploadImage()
        }
    }

    private fun uploadImage() {
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            updateImgUseCase(_state.value.avatar)
                .onSuccess { _events.send(AvatarEvents.Uploaded) }
                .onFailure { _events.send(AvatarEvents.Error(it.asUiText())) }
            _state.update { it.copy(loading = false) }

        }
    }

}