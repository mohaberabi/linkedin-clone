package com.mohaberabi.linkedin.core.domain.util

import kotlinx.coroutines.flow.Flow


sealed class AppBottomSheet(val tag: String) {
    data class JobDetailSheet(
        val jobId: String,
    ) : AppBottomSheet("jobDetailSheet")
}


sealed interface BottomSheetAction {
    data object Dismiss : BottomSheetAction
    data class Show(val appSheet: AppBottomSheet) : BottomSheetAction
}

interface AppBottomSheetShower {
    val actions: Flow<BottomSheetAction>
    fun sendAction(action: BottomSheetAction)
}

