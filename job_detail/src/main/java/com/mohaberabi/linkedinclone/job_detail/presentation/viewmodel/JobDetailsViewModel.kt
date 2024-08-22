package com.mohaberabi.linkedinclone.job_detail.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.error.RemoteError
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.linkedinclone.job_detail.domain.usecase.GetJobDetailsUseCase
import com.mohaberabi.presentation.ui.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class JobDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getJobDetailsUseCase: GetJobDetailsUseCase,
) : ViewModel() {


    private val _state = MutableStateFlow(JobDetailState())
    val state = _state.asStateFlow()

    private val id = savedStateHandle.get<String>("jobId")

    init {

        getJobDetail(id)
    }


    private fun getJobDetail(id: String?) {
        id?.let {
            _state.update { it.copy(state = JobDetailStatus.Loading) }
            viewModelScope.launch {
                getJobDetailsUseCase(id)
                    .onFailure { fail ->
                        _state.update {
                            it.copy(
                                state = JobDetailStatus.Error,
                                error = fail.asUiText()
                            )
                        }
                    }
                    .onSuccess { detail ->
                        _state.update {
                            it.copy(
                                state = JobDetailStatus.Populated,
                                detail = detail
                            )
                        }
                    }
            }
        } ?: run {
            _state.update {
                it.copy(
                    error = RemoteError.UNKNOWN_ERROR.asUiText(),
                    state = JobDetailStatus.Error
                )
            }
        }

    }
}