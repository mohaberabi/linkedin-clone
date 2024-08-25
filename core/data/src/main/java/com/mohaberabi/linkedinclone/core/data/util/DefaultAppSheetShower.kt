package com.mohaberabi.linkedinclone.core.data.util

import com.mohaberabi.linkedin.core.domain.util.AppBottomSheetShower
import com.mohaberabi.linkedin.core.domain.util.BottomSheetAction
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject


class DefaultAppSheetShower @Inject constructor(
) : AppBottomSheetShower {
    private val _actions = Channel<BottomSheetAction>()
    override val actions: Flow<BottomSheetAction>
        get() = _actions.receiveAsFlow()

    override suspend fun sendAction(
        action: BottomSheetAction,
    ) {
        _actions.send(action)
    }
}