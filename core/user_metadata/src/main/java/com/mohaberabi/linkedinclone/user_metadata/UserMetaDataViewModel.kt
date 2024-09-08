package com.mohaberabi.linkedinclone.user_metadata

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.linkedin.core.domain.model.UserMetaData
import com.mohaberabi.linkedin.core.domain.util.onFailure
import com.mohaberabi.linkedin.core.domain.util.onSuccess
import com.mohaberabi.linkedinclone.user_metadata.usecase.ListenToUserMetaDataUseCase
import com.mohaberabi.linkedinclone.user_metadata.usecase.MarkAllNotificationsReadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserMetaDataViewModel @Inject constructor(
    private val listenToUserMetaDataUseCase: ListenToUserMetaDataUseCase,
    private val markAllNotificationsReadUseCase: MarkAllNotificationsReadUseCase
) : ViewModel() {
    val state = listenToUserMetaDataUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            UserMetaData()
        )


    fun markAllNotificationsRead() {
        viewModelScope.launch {
            markAllNotificationsReadUseCase()
                .onSuccess { Log.d("meta", "Done") }
                .onFailure { Log.e("meta", it.toString()) }
        }
    }
}