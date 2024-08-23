package com.mohaberabi.linkedinclone.add_post.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class AddPostViewModel @Inject constructor() : ViewModel() {


    private val _state = MutableStateFlow(AddPostState())
    val state = _state.asStateFlow()

    fun action(action: AddPostActions) {
        when (action) {
            is AddPostActions.SavePostImage -> _state.update {
                it.copy(postImgByteArray = action.imgBytes)
            }

            AddPostActions.PickPostImage -> Unit
        }
    }
}