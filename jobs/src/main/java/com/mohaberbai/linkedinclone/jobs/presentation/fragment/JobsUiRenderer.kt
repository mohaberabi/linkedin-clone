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


fun FragmentJobsFragmentsBinding.render(
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

    pullRefresh.setOnRefreshListener {
        onRefresh()
    }
    error.hide()
    loader.hide()
    recyclerView.visibility = View.VISIBLE
    adapter.submitList(jobs)
    recyclerView.layoutManager = LinearLayoutManager(root.context)
    recyclerView.adapter = adapter
    recyclerView.addOnScrollListener(
        AppRecyclerViewScrollListener(
            isLinear = true,
        ) { lastVisible, total ->
            if (lastVisible == total - 1) {
                onLoadMore()
            }
        },
    )
    pullRefresh.isRefreshing = false

}