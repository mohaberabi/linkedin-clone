package com.mohaberabi.saved_posts.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.api.Distribution.BucketOptions.Linear
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedinclone.post_reactions.presentation.PostReactionParams
import com.mohaberabi.linkedinclone.post_reactions.presentation.ReactToPostActions
import com.mohaberabi.linkedinclone.post_reactions.presentation.ReactToPostViewModel
import com.mohaberabi.presentation.ui.list_adapters.PostsListAdapter
import com.mohaberabi.presentation.ui.navigation.AppRoutes
import com.mohaberabi.presentation.ui.navigation.goTo
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.util.extension.hideAll
import com.mohaberabi.presentation.ui.util.extension.show
import com.mohaberabi.presentation.ui.util.extension.submitIfDifferent
import com.mohaberabi.presentation.ui.util.extension.submitOnce
import com.mohaberabi.presentation.ui.views.PostClickCallBacks
import com.mohaberabi.presentation.ui.views.showReactionDialog
import com.mohaberabi.saved_posts.R
import com.mohaberabi.saved_posts.databinding.FragmentSavedPostsBinding
import com.mohaberabi.saved_posts.presentation.viewmodel.SavedPostsState
import com.mohaberabi.saved_posts.presentation.viewmodel.SavedPostsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SavedPostsFragment : Fragment() {


    private lateinit var postsListAdapter: PostsListAdapter
    private val viewModel by viewModels<SavedPostsViewModel>()
    private val reactToPostViewModel by viewModels<ReactToPostViewModel>()

    private var _binding: FragmentSavedPostsBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentSavedPostsBinding.inflate(
            layoutInflater,
            container,
            false
        )

        val postClickCallbacks = PostClickCallBacks(
            onClick = { post ->
                gotoPostDetails(postId = post.id)
            },
            onLongClickLike = { post ->

                requireContext().showReactionDialog { reaction ->
                    reactToPost(
                        post = post,
                        reactionType = reaction,
                    )
                }
            },
            onLikeClick = { post ->
                val reaction = post.currentUserReaction ?: ReactionType.Like
                reactToPost(
                    post = post,
                    reactionType = reaction,
                )
            },
        )
        postsListAdapter = PostsListAdapter(
            onClickCallBacks = postClickCallbacks
        )
        setupBinding()
        collectLifeCycleFlow(
            viewModel.state,
        ) { state ->
            bindWithState(state)
        }
        return binding.root

    }

    private fun setupBinding() {
        with(binding) {
            recyclerView.submitOnce(
                newLayoutManager = LinearLayoutManager(requireContext()),
                listAdapter = postsListAdapter,
            )
        }
    }

    private fun bindWithState(
        state: SavedPostsState,
    ) {
        with(binding) {
            when (state) {
                SavedPostsState.Error -> {
                    hideAll(
                        recyclerView,
                        loader,
                    )
                    error.show()
                }

                SavedPostsState.Loading -> {
                    hideAll(
                        recyclerView,
                        error
                    )
                    loader.show()
                }

                is SavedPostsState.Populated -> {
                    postsListAdapter.submitIfDifferent(
                        state.savedPosts,
                    )
                    hideAll(
                        loader, error,
                    )
                    recyclerView.show()
                }
            }
        }

    }

    private fun reactToPost(
        reactionType: ReactionType,
        post: PostModel,
    ) {
        val params = PostReactionParams(
            postId = post.id,
            reactionType = reactionType,
            previousReactionType = post.currentUserReaction,
            postOwnerId = post.issuerUid,
            postHeader = post.postData.lines().firstOrNull() ?: ""
        )
        reactToPostViewModel.onAction(ReactToPostActions.ReactToPost(params))

    }

    private fun gotoPostDetails(postId: String) =
        findNavController().goTo(AppRoutes.PostDetail(postId))
}

