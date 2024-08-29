package com.mohaberabi.linkedinclone.post_detail.presetnation.post_reactions.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.mohaberabi.core.presentation.ui.databinding.ReactionTabViewBinding
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedinclone.post_detail.databinding.FragmentPostReactionsBinding
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_reactions.viewmodel.PostReactionsViewModel
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.util.extension.icon
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostReactionsFragment : Fragment() {


    private lateinit var reactorsListAdapter: ReactorsListAdapter

    private val viewModel by viewModels<PostReactionsViewModel>()
    private var _binding: FragmentPostReactionsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        reactorsListAdapter = ReactorsListAdapter()
        _binding = FragmentPostReactionsBinding.inflate(
            layoutInflater,
            container,
            false
        )

        binding.setUp(
            listAdapter = reactorsListAdapter,
            onAction = viewModel::onAction
        )

        collectLifeCycleFlow(
            viewModel.state,
        ) { state ->
            binding.bindWithReactionsState(
                listAdapter = reactorsListAdapter,
                state = state
            )
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}