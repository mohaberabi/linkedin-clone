package com.mohaberbai.linkedinclone.jobs.presentation.listing.fragment

import com.mohaberbai.linkedinclone.jobs.presentation.details.fragment.JobDetailFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohaberabi.jobs.databinding.FragmentJobsFragmentsBinding
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.screenClosed
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.screenOpened
import com.mohaberabi.presentation.ui.util.AppRecyclerViewScrollListener
import com.mohaberbai.linkedinclone.jobs.presentation.listing.viewmodel.JobsViewModel
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.util.extension.submitOnce
import com.mohaberbai.linkedinclone.jobs.presentation.listing.viewmodel.JobsActions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class JobsFragments : Fragment() {
 
    private val viewModel by viewModels<JobsViewModel>()
    private var _binding: FragmentJobsFragmentsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: JobsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJobsFragmentsBinding.inflate(
            layoutInflater,
            container,
            false
        )
        adapter = JobsListAdapter(
            onClick = {
                showJobDetailSheet(it.id)
            },
        )

        setupBinding()
        collectLifeCycleFlow(
            viewModel.state,
        ) { state ->
            binding.bindWithState(
                state = state,
                jobsListAdapter = adapter
            )

        }
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    private fun showJobDetailSheet(
        jobId: String,
    ) {

        val detailSheet = JobDetailFragment.newInstance(jobId)
        detailSheet.show(parentFragmentManager, JobDetailFragment::class.simpleName)
    }

    private fun setupBinding() {
        with(binding) {
            recyclerView.submitOnce(
                listAdapter = adapter,
                scrollListener = AppRecyclerViewScrollListener(
                    isLinear = true,
                    onPaginate = {
                        viewModel.onAction(JobsActions.LoadMore)
                    }
                ),
                newLayoutManager = LinearLayoutManager(requireContext())
            )
            pullRefresh.setOnRefreshListener {
                viewModel.onAction(JobsActions.Refresh)
            }
        }
    }

}

