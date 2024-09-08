package com.mohaberabi.linkedinclone.profile_views.presentation.fragments

import androidx.recyclerview.widget.LinearLayoutManager
import com.mohaberabi.linkedinclone.profile_views.presentation.fragments.list_adapter.ProfileViewersListAdapter
import com.mohaberabi.linkedinclone.profile_views.presentation.viewmodel.ProfileViewStatus
import com.mohaberabi.linkedinclone.profile_views.presentation.viewmodel.ProfileViewsActions
import com.mohaberabi.linkedinclone.profile_views.presentation.viewmodel.ProfileViewsState
import com.mohaberabi.presentation.ui.util.AppRecyclerViewScrollListener
import com.mohaberabi.presentation.ui.util.extension.hideAll
import com.mohaberabi.presentation.ui.util.extension.show
import com.mohaberabi.presentation.ui.util.extension.submitIfDifferent
import com.mohaberabi.presentation.ui.util.extension.submitOnce
import com.mohaberabi.profile_views.databinding.FragmentProfileViewsBinding


fun FragmentProfileViewsBinding.setup(
    listAdapter: ProfileViewersListAdapter,
    onAction: (ProfileViewsActions) -> Unit
) {
    pullRefresh.setOnRefreshListener {
        onAction(ProfileViewsActions.Refresh)
    }
    recyclerView.submitOnce(
        newLayoutManager = LinearLayoutManager(root.context),
        listAdapter = listAdapter,
        scrollListener = AppRecyclerViewScrollListener(
            onPaginate = {
                onAction(ProfileViewsActions.LoadMore)

            },
            isLinear = true,
            threshold = 5
        )
    )
}

fun FragmentProfileViewsBinding.bindFromState(
    state: ProfileViewsState,
    adapter: ProfileViewersListAdapter,
) {

    when (state.state) {
        ProfileViewStatus.Error -> {
            hideAll(
                recyclerView,
                loader
            )
            error.show()
            error.setErrorTitle(state.error)
        }

        ProfileViewStatus.Populated -> {
            pullRefresh.isRefreshing = false
            hideAll(
                loader,
                error
            )
            recyclerView.show()
            adapter.submitIfDifferent(
                state.profileViews,
            )
        }

        else -> {
            pullRefresh.isRefreshing = false
            hideAll(
                error,
                recyclerView,
            )
            loader.show()
        }
    }
}