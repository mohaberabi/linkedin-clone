package com.mohaberabi.linkedinclone.jobs.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.mohaberabi.jobs.R
import com.mohaberabi.jobs.databinding.FragmentJobsFragmentsBinding
import com.mohaberabi.presentation.ui.navigation.NavDeepLinks
import com.mohaberabi.linkedinclone.jobs.presentation.viewmodel.JobsViewModel
import com.mohaberabi.presentation.ui.navigation.deepLinkNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class JobsFragments : Fragment() {


    private val viewModel by viewModels<JobsViewModel>()
    private var _binding: FragmentJobsFragmentsBinding? = null
    private val binding get() = _binding!!
    private lateinit var jobsRenderer: JobsUiRenderer
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
                goToJobDetails(it.id)
            },
        )
        jobsRenderer = JobsUiRenderer(
            onAction = viewModel::onAction,
            adapter = adapter,
            binding = binding
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                jobsRenderer.render(state)
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun goToJobDetails(id: String) {
        findNavController().deepLinkNavigate(
            NavDeepLinks.Job_Detail,
            args = listOf(
                "jobId" to id,
            )
        )
    }
}

