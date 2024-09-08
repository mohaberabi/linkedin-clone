package com.mohaberbai.linkedinclone.jobs.presentation.listing.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.mohaberabi.jobs.databinding.FragmentJobsFragmentsBinding
import com.mohaberabi.linkedin.core.domain.model.JobModel
import com.mohaberbai.linkedinclone.jobs.presentation.listing.viewmodel.JobsActions
import com.mohaberbai.linkedinclone.jobs.presentation.listing.viewmodel.JobsState
import com.mohaberbai.linkedinclone.jobs.presentation.listing.viewmodel.JobsStatus
import com.mohaberabi.presentation.ui.util.AppRecyclerViewScrollListener
import com.mohaberabi.presentation.ui.util.extension.hideAll
import com.mohaberabi.presentation.ui.util.extension.show
import com.mohaberabi.presentation.ui.util.extension.submitIfDifferent
import com.mohaberabi.presentation.ui.util.extension.submitOnce


fun FragmentJobsFragmentsBinding.bindWithState(
    state: JobsState,
    jobsListAdapter: JobsListAdapter,
) {
    when (state.state) {
        JobsStatus.Error -> {
            hideAll(
                recyclerView,
                loader,
            )
            error.show()
            error.setErrorTitle(state.error)
            pullRefresh.isRefreshing = false
        }

        JobsStatus.Populated -> {
            jobsListAdapter.submitIfDifferent(state.jobs)
            hideAll(
                error,
                loader,
            )
            recyclerView.show()
            pullRefresh.isRefreshing = false
        }

        else -> {
            loader.show()
            hideAll(
                recyclerView,
                error
            )
        }
    }
}


