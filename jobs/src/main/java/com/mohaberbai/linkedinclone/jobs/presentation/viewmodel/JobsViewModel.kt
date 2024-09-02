package com.mohaberbai.linkedinclone.jobs.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.usecase.user.ListenToCurrentUserUseCase
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberbai.linkedinclone.jobs.usecase.GetJobsUseCase
import com.mohaberabi.presentation.ui.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
    private val getJobsUseCase: GetJobsUseCase,
    private val listenToCurrentUserUseCase: ListenToCurrentUserUseCase,
) : ViewModel() {


    private val _state = MutableStateFlow(JobsState())
    val state = _state.asStateFlow()


    private var pagingJob: Job? = null

    init {
        listenToUser()
        getJobs()
    }


    private fun listenToUser() {
        listenToCurrentUserUseCase().onEach { user ->
            _state.update { it.copy(userImg = user?.img ?: "") }
        }.launchIn(viewModelScope)
    }

    fun onAction(action: JobsActions) {
        when (action) {
            JobsActions.LoadMore -> loadMore()
            JobsActions.Refresh -> getJobs()
        }
    }


    private fun loadMore() {

        val stateVal = _state.value
        pagingJob?.cancel()
        pagingJob = viewModelScope.launch {
            getJobsUseCase(lastElementId = stateVal.jobs.lastOrNull()?.id)
                .onSuccess { jobs ->
                    _state.update {
                        it.copy(
                            jobs = it.jobs + jobs,
                        )
                    }
                }
        }
    }

    private fun getJobs() {
        _state.update { it.copy(state = JobsStatus.Loading) }
        viewModelScope.launch {
            getJobsUseCase()
                .onFailure { fail ->
                    _state.update {
                        it.copy(
                            error = fail.asUiText(),
                            state = JobsStatus.Error
                        )
                    }
                }
                .onSuccess { jbs ->
                    _state.update {
                        it.copy(
                            jobs = jbs,
                            state = JobsStatus.Populated
                        )
                    }
                }
        }
    }
}