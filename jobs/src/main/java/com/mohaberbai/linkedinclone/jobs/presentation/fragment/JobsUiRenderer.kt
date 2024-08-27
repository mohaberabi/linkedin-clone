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
import com.mohaberabi.presentation.ui.util.submitIfDifferent
import com.mohaberabi.presentation.ui.util.submitOnce


fun FragmentJobsFragmentsBinding.bind(
    state: JobsState,
    onActions: (JobsActions) -> Unit,
    adapter: JobsListAdapter,
) {


    when (state.state) {
        JobsStatus.Error -> error(state.error)
        JobsStatus.Populated -> populated(
            jobs = state.jobs,
            adapter = adapter,
            onLoadMore = { onActions(JobsActions.LoadMore) },
            onRefresh = { onActions(JobsActions.Refresh) }
        )

        else -> loading()
    }
}

private fun FragmentJobsFragmentsBinding.loading() {
    loader.show()
    recyclerView.visibility = android.view.View.GONE
    error.hide()
}

private fun FragmentJobsFragmentsBinding.error(errorText: UiText) {
    val errorString = errorText.asString(root.context)
    error.show()
    error.setErrorTitle(errorString)
    loader.hide()
    recyclerView.visibility = android.view.View.GONE
    pullRefresh.isRefreshing = false

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
    error.hide()
    loader.hide()
    recyclerView.visibility = View.VISIBLE

    pullRefresh.isRefreshing = false

}