package com.mohaberabi.linkedinclone.presentation.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.usecase.ListenToCurrentUserUseCase
import com.mohaberabi.linkedinclone.presentation.activity.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getUserUseCase: ListenToCurrentUserUseCase,
) : ViewModel() {


    private val _state = MutableStateFlow(MainActivityState())

    val state = _state.asStateFlow()


    init {
        listenToUser()
    }


    private fun listenToUser() {
        getUserUseCase().onEach { currentUser ->
            _state.update { it.copy(user = currentUser, didLoad = true) }
        }.launchIn(viewModelScope)
    }
}