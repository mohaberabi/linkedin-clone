package com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mohaberabi.linkedinclone.post_detail.R
import com.mohaberabi.linkedinclone.post_detail.databinding.FragmentPostDetailBinding
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.fragments.list_adapters.CommentorListAdapter
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.fragments.list_adapters.PostDetailReactorsAdapter
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel.PostDetailViewModel
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel.PostDetailsEvents
import com.mohaberabi.presentation.ui.navigation.AppRoutes
import com.mohaberabi.presentation.ui.navigation.goTo
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.util.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostDetailFragment : Fragment() {


    private lateinit var commentorListAdapter: CommentorListAdapter
    private lateinit var reactorsListAdapter: PostDetailReactorsAdapter
    private var _binding: FragmentPostDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PostDetailViewModel>()
    private val args: PostDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPostDetailBinding.inflate(
            layoutInflater,
            container,
            false
        )

        commentorListAdapter = CommentorListAdapter()
        reactorsListAdapter = PostDetailReactorsAdapter()
        collectLifeCycleFlow(
            viewModel.state,
        ) { state ->
            binding.bind(
                state = state,
                commentAdapter = commentorListAdapter,
                reactorsAdapter = reactorsListAdapter,
                onAction = viewModel::onAction
            )
        }

        binding.moreIcon.setOnClickListener {
            goToPostReactions()
        }

        collectLifeCycleFlow(viewModel.event) { event ->
            when (event) {
                is PostDetailsEvents.Error -> binding.root.showSnackBar(event.error)
            }
        }
        return binding.root
    }


    private fun goToPostReactions() {
        val action =
            PostDetailFragmentDirections.actionPostDetailFragmentToPostReactionsFragment(args.postId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}