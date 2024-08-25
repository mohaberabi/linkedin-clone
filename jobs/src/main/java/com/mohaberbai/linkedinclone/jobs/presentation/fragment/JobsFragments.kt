package com.mohaberbai.linkedinclone.jobs.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mohaberabi.jobs.databinding.FragmentJobsFragmentsBinding
import com.mohaberabi.linkedin.core.domain.util.AppDrawerActions
import com.mohaberabi.linkedin.core.domain.util.DrawerController
import com.mohaberabi.presentation.ui.navigation.NavDeepLinks
import com.mohaberbai.linkedinclone.jobs.presentation.viewmodel.JobsViewModel
import com.mohaberabi.presentation.ui.navigation.deepLinkNavigate
import com.mohaberabi.presentation.ui.util.collectLifeCycleFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class JobsFragments : Fragment() {


    private val viewModel by viewModels<JobsViewModel>()
    private var _binding: FragmentJobsFragmentsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: JobsListAdapter

    @Inject
    lateinit var drawerController: DrawerController
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

        collectLifeCycleFlow(viewModel.state) { state ->
            binding.render(
                state = state,
                onActions = viewModel::onAction,
                adapter = adapter,
            )

        }

        binding.appBar.setOnAvatarClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                drawerController.sendDrawerAction(AppDrawerActions.Close)
                drawerController.sendDrawerAction(AppDrawerActions.Open)
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

