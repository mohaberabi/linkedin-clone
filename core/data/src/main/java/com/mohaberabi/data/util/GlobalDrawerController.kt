package com.mohaberabi.data.util

import com.mohaberabi.linkedin.core.domain.util.AppDrawerActions
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedin.core.domain.util.DrawerController
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject


class GlobalDrawerController @Inject constructor(
) : DrawerController {
    private val _events = Channel<AppDrawerActions>()
    private val flow = _events.receiveAsFlow()
    override suspend fun sendDrawerAction(
        actions: AppDrawerActions,
    ) = _events.send(actions)


    override suspend fun collect(
        collector: (AppDrawerActions) -> Unit,
    ) {
        flow.collect { act ->
            collector(act)
        }
    }


}