package com.mohaberbai.linkedinclone.jobs.presentation.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohaberabi.jobs.databinding.FragmentJobsFragmentsBinding
import com.mohaberabi.linkedin.core.domain.model.JobModel
import com.mohaberbai.linkedinclone.jobs.presentation.viewmodel.JobsActions
import com.mohaberbai.linkedinclone.jobs.presentation.viewmodel.JobsState
import com.mohaberbai.linkedinclone.jobs.presentation.viewmodel.JobsStatus
import com.mohaberabi.presentation.ui.util.AppRecyclerViewScrollListener
import com.mohaberabi.presentation.ui.util.UiText
import com.mohaberabi.presentation.ui.util.extension.hideAll
import com.mohaberabi.presentation.ui.util.extension.show
import com.mohaberabi.presentation.ui.util.extension.submitIfDifferent
import com.mohaberabi.presentation.ui.util.extension.submitOnce


fun FragmentJobsFragmentsBinding.bind(
    state: JobsState,
    onActions: (JobsActions) -> Unit,
    adapter: JobsListAdapter,
) {
    when (state.state) {
        JobsStatus.Error -> {
            val errorString = state.error.asString(root.context)
            hideAll(
                recyclerView,
                loader,
            )
            error.show()
            error.setErrorTitle(errorString)
            pullRefresh.isRefreshing = false
        }

        JobsStatus.Populated -> populated(
            jobs = state.jobs,
            adapter = adapter,
            onLoadMore = { onActions(JobsActions.LoadMore) },
            onRefresh = { onActions(JobsActions.Refresh) }
        )

        else -> {
            loader.show()
            hideAll(
                recyclerView,
                error
            )
        }
    }
}


private fun FragmentJobsFragmentsBinding.populated(
    jobs: List<JobModel>,
    adapter: JobsListAdapter,
    onLoadMore: () -> Unit,
    onRefresh: () -> Unit,
) {

    adapter.submitIfDifferent(
        jobs
    )
    recyclerView.submitOnce(
        listAdapter = adapter,
        scrollListener = AppRecyclerViewScrollListener(
            isLinear = true,
        ) {
            onLoadMore()
        },
        newLayoutManager = LinearLayoutManager(root.context)
    )
    pullRefresh.setOnRefreshListener {
        onRefresh()
    }
    hideAll(
        error,
        loader,
    )
    recyclerView.show()
    pullRefresh.isRefreshing = false

}