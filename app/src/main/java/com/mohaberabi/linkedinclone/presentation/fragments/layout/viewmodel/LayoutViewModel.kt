package com.mohaberabi.linkedinclone.presentation.fragments.layout.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.usecase.ListenToCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class LayoutViewModel @Inject constructor(
    private val listenToCurrentUserUseCase: ListenToCurrentUserUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(LayoutState())
    val state = _state.asStateFlow()


    init {

        listenToCurrentUserUseCase().onEach { user ->
            _state.update { it.copy(user = user) }
        }.launchIn(viewModelScope)
    }
}