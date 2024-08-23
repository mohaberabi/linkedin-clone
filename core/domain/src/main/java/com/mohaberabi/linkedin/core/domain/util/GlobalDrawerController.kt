package com.mohaberabi.linkedin.core.domain.util

sealed interface AppDrawerActions {
    data object Open : AppDrawerActions
    data object Close : AppDrawerActions
}

interface DrawerController {
    suspend fun sendDrawerAction(actions: AppDrawerActions)
    suspend fun collect(collector: (AppDrawerActions) -> Unit)
}