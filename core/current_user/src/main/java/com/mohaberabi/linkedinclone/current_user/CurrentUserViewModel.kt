package com.mohaberabi.linkedinclone.current_user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.linkedin.core.domain.usecase.user.ListenToCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


data class CurrentUserState(
    val user: UserModel? = null,
    val didLoad: Boolean = false,
)

@HiltViewModel
class CurrentUserViewModel @Inject constructor(
    getUserUseCase: ListenToCurrentUserUseCase,
) : ViewModel() {
    val state =
        getUserUseCase().map { user ->
            CurrentUserState(
                didLoad = true,
                user = user
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            CurrentUserState()
        )
}