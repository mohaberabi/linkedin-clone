package com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedinclone.post_detail.databinding.FragmentPostDetailBinding
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.fragments.list_adapters.CommentorListAdapter
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.fragments.list_adapters.PostDetailReactorsAdapter
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel.PostDetailActions
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel.PostDetailViewModel
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel.PostDetailsEvents
import com.mohaberabi.presentation.ui.util.AppRecyclerViewScrollListener
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.util.extension.showSnackBar
import com.mohaberabi.presentation.ui.util.extension.submitOnce
import com.mohaberabi.presentation.ui.views.post_item.PostClickCallBacks
import com.mohaberabi.presentation.ui.views.showReactionDialog
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
        binding.setup(
            onAction = viewModel::onAction,
            commentAdapter = commentorListAdapter,
            reactorsAdapter = reactorsListAdapter
        )
        binding.moreIcon.setOnClickListener {
            goToPostReactions()
        }

        val postClickCallBacks = PostClickCallBacks(
            onLikeClick = { post ->
                val reaction = post.currentUserReaction ?: ReactionType.Like
                reactToPost(
                    reactionType = reaction,
                    previousReactionType = post.currentUserReaction
                )
            },
            onLongClickLike = { post ->
                requireContext().showReactionDialog { reaction ->
                    reactToPost(
                        reactionType = reaction,
                        previousReactionType = post.currentUserReaction
                    )
                }
            }
        )
        collectLifeCycleFlow(
            viewModel.state,
        ) { state ->
            binding.bindWithState(
                state = state,
                commentAdapter = commentorListAdapter,
                reactorsAdapter = reactorsListAdapter,
                onPostClickCallbacks = postClickCallBacks,
            )
        }
        collectLifeCycleFlow(
            viewModel.event,
        ) { event ->
            when (event) {
                is PostDetailsEvents.Error -> binding.root.showSnackBar(event.error)
            }
        }
        return binding.root
    }

    private fun reactToPost(
        reactionType: ReactionType = ReactionType.Like,
        previousReactionType: ReactionType?
    ) {
        viewModel.onAction(
            PostDetailActions.ReactOnPost(
                reactionType = reactionType,
                previousReactionType = previousReactionType,
            )
        )
    }

    private fun goToPostReactions() {
        val action =
            PostDetailFragmentDirections.actionPostDetailFragmentToPostReactionsFragment(
                args.postId,
            )
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}