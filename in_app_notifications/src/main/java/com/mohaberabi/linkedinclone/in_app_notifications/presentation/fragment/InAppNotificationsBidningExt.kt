package com.mohaberabi.linkedinclone.in_app_notifications.presentation.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.mohaberabi.in_app_notifications.databinding.FragmentInAppNotificationsBinding
import com.mohaberabi.linkedinclone.in_app_notifications.presentation.fragment.list_adapter.InAppNotificationsListAdapter
import com.mohaberabi.linkedinclone.in_app_notifications.presentation.viewmodel.InAppNotiStatus
import com.mohaberabi.linkedinclone.in_app_notifications.presentation.viewmodel.InAppNotificationsState
import com.mohaberabi.presentation.ui.util.AppRecyclerViewScrollListener
import com.mohaberabi.presentation.ui.util.extension.hideAll
import com.mohaberabi.presentation.ui.util.extension.show
import com.mohaberabi.presentation.ui.util.extension.submitIfDifferent
import com.mohaberabi.presentation.ui.util.extension.submitOnce


fun FragmentInAppNotificationsBinding.bindWithState(
    state: InAppNotificationsState,
    adapter: InAppNotificationsListAdapter,
) {

    when (state.state) {
        InAppNotiStatus.Error -> {
            hideAll(
                loader,
                notificationsRecyclerView,
            )
            errorWidget.apply {
                show()
                setErrorTitle(state.error.asString(root.context))
            }
        }

        InAppNotiStatus.Populated -> {
            adapter.submitIfDifferent(
                state.notifications,
            )
            hideAll(
                loader,
                errorWidget,
            )
            notificationsRecyclerView.show()
        }

        else -> {
            hideAll(
                notificationsRecyclerView,
                errorWidget,
            )
            loader.show()
        }
    }
}