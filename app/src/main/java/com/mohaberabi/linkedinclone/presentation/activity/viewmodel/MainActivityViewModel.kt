package com.mohaberabi.linkedinclone.presentation.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.usecase.user.ListenToCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    getUserUseCase: ListenToCurrentUserUseCase,
) : ViewModel() {
    val state =
        getUserUseCase().map { user ->
            MainActivityState(
                didLoad = true,
                user = user
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            MainActivityState()
        )

}