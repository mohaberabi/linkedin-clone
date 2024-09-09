package com.mohaberbai.linkedinclone.jobs.presentation.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics
import com.mohaberabi.linkedin.core.domain.error.RemoteError
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.presentation.ui.util.asUiText
import com.mohaberbai.linkedinclone.jobs.presentation.details.fragment.logJobDetails
import com.mohaberbai.linkedinclone.jobs.usecase.GetJobDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class JobDetailsViewModel @Inject constructor(
    private val getJobDetailsUseCase: GetJobDetailsUseCase,
    private val appAnalytics: AppAnalytics,
) : ViewModel() {
    private val _state = MutableStateFlow(JobDetailState())
    val state = _state.asStateFlow()
    fun onAction(action: JobDetailActions) {
        when (action) {
            is JobDetailActions.JobIdChanged -> getJobDetail(action.id)
        }
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
                        logJobDetailOnDone(detail.id)
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


    private fun logJobDetailOnDone(id: String) = appAnalytics.logJobDetails(id)
}