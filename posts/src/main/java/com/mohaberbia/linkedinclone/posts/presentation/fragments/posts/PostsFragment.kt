package com.mohaberbia.linkedinclone.posts.presentation.fragments.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mohaberbia.linkedinclone.posts.presentation.viewmodel.PostsViewModel
import com.mohaberabi.posts.databinding.FragmentPostsBinding
import com.mohaberabi.presentation.ui.util.collectLifeCycleFlow
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostsFragment : Fragment() {
    private val viewModel: PostsViewModel by viewModels()
    private lateinit var postsListAdapter: PostsListAdapter
    private lateinit var binding: FragmentPostsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostsBinding.inflate(
            inflater,
            container,
            false
        )
        postsListAdapter = PostsListAdapter()
        collectLifeCycleFlow(
            viewModel.state,
        ) { state ->
            binding.bind(
                state = state,
                onAction = viewModel::onAction,
                adapter = postsListAdapter,
            )
        }

        return binding.root
    }
}