package com.mohaberabi.linkedinclone.core.data.util

import com.mohaberabi.linkedin.core.domain.util.AppSuperVisorScope
import com.mohaberabi.linkedin.core.domain.util.GlobalNavigator
import com.mohaberabi.linkedin.core.domain.util.NavigationCommand
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class DefaultGlobalNavigator @Inject constructor(
    private val appSupervisorScope: AppSuperVisorScope
) : GlobalNavigator {
    private val _commands = Channel<NavigationCommand>()
    override val commands: Flow<NavigationCommand> = _commands.receiveAsFlow()
    override fun sendCommand(command: NavigationCommand) {
        appSupervisorScope().launch {
            _commands.send(command)
        }
    }

}