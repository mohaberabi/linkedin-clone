package com.mohaberabi.linkedinclone.posts.presentation.fragments.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mohaberabi.linkedinclone.posts.presentation.viewmodel.PostsViewModel
import com.mohaberabi.posts.databinding.FragmentPostsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PostsFragment : Fragment() {
    private val viewModel: PostsViewModel by viewModels()
    private lateinit var postsListAdapter: PostsListAdapter
    private lateinit var binding: FragmentPostsBinding
    private lateinit var stateRender: PostStateRenderer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentPostsBinding.inflate(inflater, container, false)
        postsListAdapter = PostsListAdapter()
        stateRender = PostStateRenderer(
            binding = binding,
            adapter = postsListAdapter,
            onAction = viewModel::onAction
        )
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                stateRender.render(state)
            }
        }
        return binding.root
    }
}