package com.mohaberabi.presentation.ui.navigation


sealed interface NavigationCommand {
    data class ToDestination(
        val fragUri: String,
    ) : NavigationCommand

}