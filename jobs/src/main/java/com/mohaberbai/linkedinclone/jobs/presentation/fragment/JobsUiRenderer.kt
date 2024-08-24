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


class JobsUiRenderer(
    private val adapter: JobsListAdapter,
    private val binding: FragmentJobsFragmentsBinding,
    private val onAction: (JobsActions) -> Unit,
) {


    fun render(state: JobsState) {
        when (state.state) {
            JobsStatus.Error -> error(state.error)
            JobsStatus.Populated -> populated(state.jobs)
            else -> loading()
        }
    }

    private fun loading() {
        with(binding) {
            loader.show()
            recyclerView.visibility = View.GONE
            error.hide()
        }
    }

    private fun error(errorText: UiText) {
        val errorString = errorText.asString(binding.root.context)
        with(binding) {
            error.show()
            error.setErrorTitle(errorString)
            loader.hide()
            recyclerView.visibility = View.GONE
        }
    }

    private fun populated(jobs: List<JobModel>) {
        with(binding) {
            error.hide()
            loader.hide()
            recyclerView.visibility = View.VISIBLE
            adapter.submitList(jobs)
            recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
            recyclerView.adapter = adapter
            recyclerView.addOnScrollListener(
                AppRecyclerViewScrollListener(
                    isLinear = true,
                ) { lastVisible, total ->
                    if (lastVisible == total - 1) {
                        onAction(JobsActions.LoadMore)
                    }
                },
            )
        }
    }
}