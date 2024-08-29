package com.mohaberabi.linkedinclone.add_post.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.model.AppFile
import com.mohaberabi.linkedin.core.domain.usecase.ListenToCurrentUserUseCase
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.linkedinclone.add_post.usecase.AddPostUseCase
import com.mohaberabi.presentation.ui.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val addPostUseCase: AddPostUseCase,
    private val listenToCurrentUserUseCase: ListenToCurrentUserUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(AddPostState())
    val state = _state.asStateFlow()

    init {
        listenToCurrentUserUseCase()
            .onEach { user ->
                _state.update {
                    it.copy(
                        userImg = user?.img ?: "",
                    )
                }
            }.launchIn(viewModelScope)
    }

    private val _events = Channel<AddPostEvents>()
    val events = _events.receiveAsFlow()
    fun action(action: AddPostActions) {
        when (action) {
            is AddPostActions.SavePostImage -> _state.update {
                it.copy(postImgByteArray = action.imgBytes)
            }

            AddPostActions.PickPostImage -> Unit
            is AddPostActions.PostDataChanged -> postDataChanged(action.data)
            AddPostActions.SubmitPost -> submitPost()
        }
    }


    private fun postDataChanged(
        data: String,
    ) = _state.update {
        it.copy(postData = data)
    }


    private fun submitPost() {
        val stateVal = _state.value
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            val generatedId = UUID.randomUUID().toString()
            val postImgFile = if (stateVal.postImgByteArray.isNotEmpty())
                AppFile(
                    bytes = stateVal.postImgByteArray,
                    reference = "posts/${generatedId}/images"
                )
            else null
            addPostUseCase(
                postData = stateVal.postData,
                postAttachedImg = postImgFile,
                postId = generatedId
            ).onFailure { fail ->
                _events.send(AddPostEvents.Error(fail.asUiText()))
            }.onSuccess {
                _events.send(AddPostEvents.Posted)
            }
            _state.update { it.copy(loading = false) }
        }
    }
}