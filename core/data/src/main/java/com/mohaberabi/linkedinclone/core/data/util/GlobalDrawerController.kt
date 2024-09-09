package com.mohaberabi.linkedinclone.core.data.util

import com.mohaberabi.linkedin.core.domain.util.AppDrawerActions
import com.mohaberabi.linkedin.core.domain.util.AppSuperVisorScope
import com.mohaberabi.linkedin.core.domain.util.DrawerController
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class GlobalDrawerController @Inject constructor(
) : DrawerController {
    private val _events = Channel<AppDrawerActions>()
    private val flow = _events.receiveAsFlow()
    override fun sendDrawerAction(
        actions: AppDrawerActions,
    ) {
        _events.trySend(actions)

    }


    override suspend fun collect(
        collector: (AppDrawerActions) -> Unit,
    ) {
        flow.collect { act ->
            collector(act)
        }
    }


}