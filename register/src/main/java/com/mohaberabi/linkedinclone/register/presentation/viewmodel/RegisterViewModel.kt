package com.mohaberabi.linkedinclone.register.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.linkedinclone.register.domain.usecase.RegisterUsecase
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
class RegisterViewModel @Inject constructor(
    private val registerUsecase: RegisterUsecase,
) : ViewModel() {


    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private val _event = Channel<RegisterEvents>()
    val event = _event.receiveAsFlow()


    fun onAction(action: RegisterActions) {
        when (action) {
            is RegisterActions.BioChanged -> bioChanged(action.bio)
            is RegisterActions.EmailChanged -> emailChanged(action.email)
            is RegisterActions.LastNameChanged -> lastNameChanged(action.lastname)
            is RegisterActions.NameChanged -> nameChanged(action.name)
            RegisterActions.OnRegisterClick -> register()
            is RegisterActions.PasswordChanged -> passwordChanged(action.password)
        }
    }

    private fun nameChanged(value: String) = _state.update { it.copy(name = value) }
    private fun lastNameChanged(value: String) = _state.update { it.copy(lastname = value) }
    private fun emailChanged(value: String) = _state.update { it.copy(email = value) }
    private fun passwordChanged(value: String) = _state.update { it.copy(password = value) }
    private fun bioChanged(value: String) = _state.update { it.copy(bio = value) }


    private fun register() {
        val stateVal = _state.value
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            registerUsecase(
                name = stateVal.name,
                lastname = stateVal.lastname,
                email = stateVal.email,
                password = stateVal.password,
                bio = stateVal.bio
            ).onFailure { fail ->
                _event.send(RegisterEvents.Error(fail.asUiText()))
            }.onSuccess { _event.send(RegisterEvents.Registered) }
            _state.update { it.copy(loading = false) }
        }

    }
}