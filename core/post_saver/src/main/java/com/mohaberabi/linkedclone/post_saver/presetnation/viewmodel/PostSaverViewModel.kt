package com.mohaberabi.linkedclone.post_saver.presetnation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedclone.post_saver.domain.usecase.SavePostUseCase
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.presentation.ui.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostSaverViewModel @Inject constructor(
    private val savePostUseCase: SavePostUseCase,
) : ViewModel() {


    private val _event = Channel<PostSaverEvents>()
    val event = _event.receiveAsFlow()


    fun onAction(action: PostSaverActions) {
        when (action) {
            is PostSaverActions.SavePost -> savePost(action.id)
        }
    }

    private fun savePost(id: String) {
        viewModelScope.launch {
            savePostUseCase(id)
                .onFailure { fail -> _event.send(PostSaverEvents.Error(fail.asUiText())) }
                .onSuccess { _event.send(PostSaverEvents.PostSaved) }
        }
    }
}