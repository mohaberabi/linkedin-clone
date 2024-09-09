package com.mohaberabi.saved_posts.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.saved_posts.domain.usecase.GetSavedPostsWithReactionsUseCase
import com.mohaberabi.saved_posts.domain.usecase.ListenToSavedPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SavedPostsViewModel @Inject constructor(
    listenToSavedPostsUseCase: ListenToSavedPostsUseCase,
    private val getSavedPostsWithReactionUseCase: GetSavedPostsWithReactionsUseCase,
) : ViewModel() {
    companion object {
        const val MAX_RETRY = 5
    }

    init {
        getAndSaveSavedPosts()
    }

    val state = listenToSavedPostsUseCase()
        .map {
            SavedPostsState.Populated(it) as SavedPostsState
        }.onStart {
            emit(SavedPostsState.Loading)
        }.catch {
            emit(SavedPostsState.Error)
        }.retryWhen { _, attempt ->
            if (attempt < MAX_RETRY) {
                delay((attempt + 1) * 1000L)
                true
            } else {
                false
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            SavedPostsState.Loading
        )


    private fun getAndSaveSavedPosts() {
        viewModelScope.launch {
            getSavedPostsWithReactionUseCase()
                .onFailure { }
                .onSuccess { }
        }
    }
}