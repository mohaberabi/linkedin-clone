package com.mohaberabi.linkedinclone.usermedida.profile_picture.viewmodel

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
class ProfilePictureViewModel @Inject constructor(
    private val updateImgUseCase: UpdateImgUseCase,
) : ViewModel() {


    private val _state = MutableStateFlow(ProfilePictureState())
    val state = _state.asStateFlow()
    private val _events = Channel<ProfilePictureEvents>()
    val events = _events.receiveAsFlow()
    fun onAction(action: ProfilePictureActions) {
        when (action) {
            is ProfilePictureActions.ProfilePictureChanged -> pictureChanged(action.bytes)
            ProfilePictureActions.ConfirmUpload -> uploadImage()
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
                    _events.send(ProfilePictureEvents.Uploaded)
                }
                .onFailure { fail ->
                    _events.send(ProfilePictureEvents.Error(fail.asUiText()))
                }
            _state.update { it.copy(loading = false) }

        }
    }

}