package com.mohaberbia.linkedinclone.posts.presentation.fragments.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberbia.linkedinclone.posts.presentation.viewmodel.PostsViewModel
import com.mohaberabi.posts.databinding.FragmentPostsBinding
import com.mohaberabi.presentation.ui.navigation.AppRoutes
import com.mohaberabi.presentation.ui.navigation.goTo
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.util.extension.showSnackBar
import com.mohaberabi.presentation.ui.views.post_item.PostClickCallBacks
import com.mohaberabi.presentation.ui.views.showReactionDialog
import com.mohaberbia.linkedinclone.posts.presentation.viewmodel.PostsActions
import com.mohaberbia.linkedinclone.posts.presentation.viewmodel.PostsEvents
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostsFragment : Fragment() {
    private val viewModel: PostsViewModel by viewModels()
    private lateinit var postsListAdapter: PostsListAdapter
    private lateinit var binding: FragmentPostsBinding
    private lateinit var postClickCallBacks: PostClickCallBacks

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostsBinding.inflate(
            inflater,
            container,
            false
        )
        postClickCallBacks = createPostCallBacks()
        postsListAdapter = PostsListAdapter(
            onClickCallBacks = postClickCallBacks
        )
        collectLifeCycleFlow(
            viewModel.state,
        ) { state ->
            binding.bind(
                state = state,
                onAction = viewModel::onAction,
                adapter = postsListAdapter,
            )
        }

        collectLifeCycleFlow(
            viewModel.events,
        ) { event ->
            when (event) {
                is PostsEvents.Error -> binding.root.showSnackBar(
                    event.error.asString(
                        requireContext()
                    )
                )
            }
        }
        return binding.root
    }


    private fun createPostCallBacks() =
        PostClickCallBacks(
            onClick = { post ->
                goToPostDetail(post.id)
            },
            onLikeClick = { post ->
                val reaction = post.currentUserReaction?.reactionType ?: ReactionType.Like
                reactToPost(
                    postId = post.id,
                    reactionType = reaction,
                    previousReactionType = post.currentUserReaction?.reactionType
                )
            },
            onLongClickLike = { post ->
                requireContext().showReactionDialog { reaction ->
                    reactToPost(
                        postId = post.id,
                        reactionType = reaction,
                        previousReactionType = post.currentUserReaction?.reactionType
                    )
                }
            }
        )

    private fun reactToPost(
        postId: String,
        reactionType: ReactionType = ReactionType.Like,
        previousReactionType: ReactionType?
    ) {
        viewModel.onAction(
            PostsActions.ReactToPost(
                postId = postId,
                reactionType = reactionType,
                previousReactionType = previousReactionType,
            )
        )
    }

    private fun goToPostDetail(
        postId: String,
    ) {
        findNavController().goTo(AppRoutes.PostDetail(postId))
    }
}