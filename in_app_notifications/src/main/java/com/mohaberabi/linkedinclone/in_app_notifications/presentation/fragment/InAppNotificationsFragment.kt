package com.mohaberabi.linkedinclone.in_app_notifications.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohaberabi.in_app_notifications.databinding.FragmentInAppNotificationsBinding
import com.mohaberabi.linkedinclone.in_app_notifications.presentation.fragment.list_adapter.InAppNotificationsListAdapter
import com.mohaberabi.linkedinclone.in_app_notifications.presentation.viewmodel.AppNotificationsActions
import com.mohaberabi.linkedinclone.in_app_notifications.presentation.viewmodel.InAppNotiStatus
import com.mohaberabi.linkedinclone.in_app_notifications.presentation.viewmodel.InAppNotificationsState
import com.mohaberabi.linkedinclone.in_app_notifications.presentation.viewmodel.InAppNotificationsViewModel
import com.mohaberabi.presentation.ui.navigation.AppRoutes
import com.mohaberabi.presentation.ui.navigation.goTo
import com.mohaberabi.presentation.ui.util.AppRecyclerViewScrollListener
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.util.extension.submitOnce
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InAppNotificationsFragment : Fragment() {

    private val viewmodel by viewModels<InAppNotificationsViewModel>()

    private lateinit var adapter: InAppNotificationsListAdapter
    private var _binding: FragmentInAppNotificationsBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInAppNotificationsBinding.inflate(
            layoutInflater,
            container,
            false,
        )
        adapter = InAppNotificationsListAdapter(
            onNotiPostClick = {
                goToPostDetails(it)
            },
            onNotiUserClick = {
                goToProfile(it)
            }
        )
        setupBinding()
        collectLifeCycleFlow(
            viewmodel.state,
        ) { state ->
            binding.bindWithState(
                state = state,
                adapter = adapter
            )

        }
        return binding.root
    }

    private fun setupBinding() {
        binding.notificationsRecyclerView.submitOnce(
            newLayoutManager = LinearLayoutManager(requireContext()),
            listAdapter = adapter,
            scrollListener = AppRecyclerViewScrollListener(
                onPaginate = { viewmodel.onAction(AppNotificationsActions.LoadMore) },
                isLinear = true,
                threshold = 2,
            )
        )
    }

    private fun goToPostDetails(id: String) {
        findNavController().goTo(AppRoutes.PostDetail(id))
    }

    private fun goToProfile(id: String) {
        findNavController().goTo(AppRoutes.Profile(id))
    }
}