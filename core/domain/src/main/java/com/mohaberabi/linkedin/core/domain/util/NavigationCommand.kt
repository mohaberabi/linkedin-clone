package com.mohaberabi.linkedin.core.domain.util

import kotlinx.coroutines.flow.Flow


sealed interface NavigationCommand {

    data class GoTo(
        val destinationUri: String,
    ) : NavigationCommand
}

interface GlobalNavigator {
    val commands: Flow<NavigationCommand>
    fun sendCommand(command: NavigationCommand)
}