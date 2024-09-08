package com.mohaberabi.linkedinclone.presentation.activity.viewmodel

import androidx.lifecycle.ViewModel
import com.mohaberabi.linkedinclone.presentation.activity.isTopLevelRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(MainActivityViewsState())
    val state = _state.asStateFlow()
    fun changeDestination(
        id: Int,
        title: CharSequence? = null,
    ) {
        _state.update { it.copy(toolBarTitle = title) }
        if (id.isTopLevelRoute) {
            _state.update { MainActivityViewsState() }
        } else {
            _state.update { it.nonTopLevel }
        }
    }
}

