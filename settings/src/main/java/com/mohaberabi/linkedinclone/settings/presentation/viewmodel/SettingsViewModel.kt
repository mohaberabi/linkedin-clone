package com.mohaberabi.linkedinclone.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.linkedinclone.settings.domain.usecase.LogUserSignedOutUseCase
import com.mohaberabi.linkedinclone.settings.domain.usecase.SignOutUseCase
import com.mohaberabi.presentation.ui.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.sign

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val logUserSignedOutUseCase: LogUserSignedOutUseCase,
) : ViewModel() {


    private val _event = Channel<SettingsEvents>()
    val event = _event.receiveAsFlow()


    fun onAction(action: SettingsActions) {
        when (action) {
            SettingsActions.SignOut -> signOut()
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            signOutUseCase()
                .onSuccess {
                    _event.send(SettingsEvents.SignedOut)
                    logUserSignedOutUseCase()
                }
                .onFailure { fail -> _event.send(SettingsEvents.Error(fail.asUiText())) }
        }
    }
}