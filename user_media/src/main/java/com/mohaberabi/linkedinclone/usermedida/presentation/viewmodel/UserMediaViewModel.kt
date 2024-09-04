package com.mohaberabi.linkedinclone.usermedida.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.linkedinclone.usermedida.domain.usecase.UpdateCoverUseCase
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
    private val updateCoverUseCase: UpdateCoverUseCase,
) : ViewModel() {


    private val _state = MutableStateFlow(UserMediaState())
    val state = _state.asStateFlow()
    private val _events = Channel<UserMediaEvents>()
    val events = _events.receiveAsFlow()
    fun onAction(action: UserMediaActions) {
        when (action) {
            is UserMediaActions.ProfilePictureChanged -> pictureChanged(action.bytes)
            is UserMediaActions.ConfirmUpload -> {
                if (action.isCover) uploadCoverImg() else uploadProfilePic()
            }
        }
    }

    private fun pictureChanged(bytes: ByteArray) {
        _state.update {
            it.copy(pickedMedia = _state.value.pickedMedia.copy(bytes = bytes))
        }
    }


    private fun uploadProfilePic() {
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            val img = _state.value.pickedMedia
            updateImgUseCase(img)
                .onSuccess {
                    _events.send(UserMediaEvents.Uploaded)
                }.onFailure { fail ->
                    _events.send(UserMediaEvents.Error(fail.asUiText()))
                }
            _state.update { it.copy(loading = false) }

        }
    }

    private fun uploadCoverImg() {
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            val img = _state.value.pickedMedia
            updateCoverUseCase(img)
                .onSuccess {
                    _events.send(UserMediaEvents.Uploaded)
                }.onFailure { fail ->
                    _events.send(UserMediaEvents.Error(fail.asUiText()))
                }
            _state.update { it.copy(loading = false) }

        }
    }


}