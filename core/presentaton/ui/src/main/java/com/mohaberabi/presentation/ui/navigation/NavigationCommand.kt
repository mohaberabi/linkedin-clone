package com.mohaberabi.presentation.ui.navigation

import kotlinx.coroutines.flow.Flow


sealed interface NavigationCommand {
    data class ClearAllAndGo(val route: AppRoutes)
}

interface GlobalNavigator {
    val commands: Flow<NavigationCommand>
    fun sendCommand(command: NavigationCommand)
}