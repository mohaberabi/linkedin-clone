package com.mohaberabi.linkedinclone.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.usecase.validator.ValidatorUseCases
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.linkedinclone.login.domain.usecase.LoginUseCase
import com.mohaberabi.linkedinclone.login.viewmodel.LoginActions
import com.mohaberabi.linkedinclone.login.viewmodel.LoginEvents
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
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val inputValidator: ValidatorUseCases,
) : ViewModel() {


    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()


    private val _events = Channel<LoginEvents>()
    val events = _events.receiveAsFlow()

    fun onAction(action: LoginActions) {
        when (action) {
            is LoginActions.EmailChanged -> emailChanged(action.email)
            is LoginActions.PasswordChanged -> passwordChanged(action.password)
            LoginActions.SubmitLogin -> login()
        }
    }


    private fun login() {
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            loginUseCase(
                email = _state.value.email,
                password = _state.value.password
            ).onFailure { fail ->
                _events.send(LoginEvents.Error(fail.asUiText()))
            }.onSuccess {
                _events.send(LoginEvents.LoggedIn)
            }
            _state.update { it.copy(loading = false) }
        }
    }

    private fun emailChanged(value: String) {
        _state.update {
            it.copy(
                email = value,
                emailValidState = inputValidator.validateEmail(value)
            )
        }
    }

    private fun passwordChanged(value: String) {
        _state.update {
            it.copy(
                password = value,
                passwordValidState = inputValidator.validatePassword(value)
            )
        }
    }
}