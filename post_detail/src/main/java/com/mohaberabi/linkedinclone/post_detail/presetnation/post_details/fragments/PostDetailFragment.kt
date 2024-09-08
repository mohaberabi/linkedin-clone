package com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedinclone.current_user.presentation.CurrentUserViewModel
import com.mohaberabi.linkedinclone.post_detail.databinding.FragmentPostDetailBinding
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.fragments.list_adapters.CommentorListAdapter
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.fragments.list_adapters.PostDetailReactorsAdapter
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel.PostDetailActions
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel.PostDetailViewModel
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel.PostDetailsEvents
import com.mohaberabi.linkedinclone.post_reactions.presentation.PostReactionParams
import com.mohaberabi.linkedinclone.post_reactions.presentation.ReactToPostActions
import com.mohaberabi.linkedinclone.post_reactions.presentation.ReactToPostViewModel
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.util.extension.showSnackBar
import com.mohaberabi.presentation.ui.views.PostClickCallBacks
import com.mohaberabi.presentation.ui.views.showReactionDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostDetailFragment : Fragment() {
    private lateinit var commentorListAdapter: CommentorListAdapter
    private lateinit var reactorsListAdapter: PostDetailReactorsAdapter
    private var _binding: FragmentPostDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PostDetailViewModel>()
    private val reactToPostViewModel: ReactToPostViewModel by viewModels()
    private val currentUserViewModel by activityViewModels<CurrentUserViewModel>()

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
            reactorsAdapter = reactorsListAdapter,
            onMoreReactionsClick = { goToPostReactions() }
        )

        val postClickCallBacks = PostClickCallBacks(
            onLikeClick = { post ->
                val reaction = post.currentUserReaction ?: ReactionType.Like
                reactToPost(
                    reactionType = reaction,
                    post = post,
                )
            },
            onLongClickLike = { post ->
                requireContext().showReactionDialog { reaction ->
                    reactToPost(
                        reactionType = reaction,
                        post = post,
                    )
                }
            }
        )

        collectLifeCycleFlow(
            currentUserViewModel.state,
        ) { state ->
            state.user?.let {
                viewModel.onAction(PostDetailActions.CurrentUIdChanged(it.uid))
            }
        }
        collectLifeCycleFlow(
            viewModel.state,
        ) { state ->
            binding.bindWithState(
                state = state,
                commentAdapter = commentorListAdapter,
                reactorsAdapter = reactorsListAdapter,
                callBacks = postClickCallBacks
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
        post: PostModel,
    ) {
        val params = PostReactionParams(
            previousReactionType = post.currentUserReaction,
            reactionType = reactionType,
            postId = post.id,
            postOwnerId = post.issuerUid,
            postHeader = post.postData.lines().firstOrNull() ?: ""
        )
        reactToPostViewModel.onAction(
            ReactToPostActions.ReactToPost(
                params = params
            )
        )
    }

    private fun goToPostReactions() {
        PostDetailFragmentDirections.actionPostDetailFragmentToPostReactionsFragment(args.postId)
            .also {
                findNavController().navigate(it)
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}