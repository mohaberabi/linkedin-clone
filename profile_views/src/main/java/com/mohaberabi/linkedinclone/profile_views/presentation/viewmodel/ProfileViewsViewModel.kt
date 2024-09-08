package com.mohaberabi.linkedinclone.profile_views.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.linkedinclone.profile_views.domain.usecase.GetProfileViewsUseCase
import com.mohaberabi.presentation.ui.util.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewsViewModel @Inject constructor(
    private val getProfileViewsUseCase: GetProfileViewsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileViewsState())
    val state = _state.asStateFlow()


    init {
        getViewers()
    }

    fun onAction(action: ProfileViewsActions) {
        when (action) {
            ProfileViewsActions.LoadMore -> loadMore()
            ProfileViewsActions.Refresh -> getViewers()
        }
    }


    private fun loadMore() {
        viewModelScope.launch {
            val stateVal = _state.value
            val lastDoc = stateVal.profileViews.lastOrNull()?.uid
            getProfileViewsUseCase(lastDocId = lastDoc)
                .onSuccess { viewers ->
                    _state.update {
                        it.copy(
                            profileViews = it.profileViews + viewers,
                        )
                    }
                }

        }
    }

    private fun getViewers() {
        _state.update { it.copy(state = ProfileViewStatus.Loading) }
        viewModelScope.launch {
            getProfileViewsUseCase()
                .onSuccess { viewers ->
                    _state.update {
                        it.copy(
                            profileViews = viewers,
                            state = ProfileViewStatus.Populated
                        )
                    }
                }.onFailure { fail ->
                    _state.update {
                        it.copy(
                            state = ProfileViewStatus.Error,
                            error = fail.asUiText(),
                        )
                    }
                }
        }
    }

}