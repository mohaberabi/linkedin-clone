package com.mohaberabi.linkedinclone.core.data.util

import com.mohaberabi.linkedin.core.domain.util.AppBottomSheetShower
import com.mohaberabi.linkedin.core.domain.util.AppSuperVisorScope
import com.mohaberabi.linkedin.core.domain.util.BottomSheetAction
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class DefaultAppSheetShower @Inject constructor(
    private val appSuperVisorScope: AppSuperVisorScope,
) : AppBottomSheetShower {
    private val _actions = Channel<BottomSheetAction>()
    override val actions: Flow<BottomSheetAction>
        get() = _actions.receiveAsFlow()

    override fun sendAction(
        action: BottomSheetAction,
    ) {
        appSuperVisorScope().launch {
            _actions.send(action)
        }
    }
}