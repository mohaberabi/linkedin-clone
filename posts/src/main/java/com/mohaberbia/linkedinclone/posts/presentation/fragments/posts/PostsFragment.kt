package com.mohaberbia.linkedinclone.posts.presentation.fragments.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.mohaberabi.linkedclone.post_saver.presetnation.fragments.PostSaverSheet
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedinclone.post_reactions.presentation.PostReactionParams
import com.mohaberabi.linkedinclone.post_reactions.presentation.ReactToPostActions
import com.mohaberabi.linkedinclone.post_reactions.presentation.ReactToPostEvents
import com.mohaberabi.linkedinclone.post_reactions.presentation.ReactToPostViewModel
import com.mohaberbia.linkedinclone.posts.presentation.viewmodel.PostsViewModel
import com.mohaberabi.posts.databinding.FragmentPostsBinding
import com.mohaberabi.presentation.ui.list_adapters.PostsListAdapter
import com.mohaberabi.presentation.ui.navigation.AppRoutes
import com.mohaberabi.presentation.ui.navigation.goTo
import com.mohaberabi.presentation.ui.util.AppRecyclerViewScrollListener
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.util.extension.showSnackBar
import com.mohaberabi.presentation.ui.util.extension.submitOnce
import com.mohaberabi.presentation.ui.views.PostClickCallBacks
import com.mohaberabi.presentation.ui.views.showReactionDialog
import com.mohaberbia.linkedinclone.posts.presentation.viewmodel.PostsActions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostsFragment : Fragment() {

    private val reactToPostViewModel: ReactToPostViewModel by viewModels()

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
        binding.setup(
            onAction = viewModel::onAction,
            postsListAdapter = postsListAdapter
        )
        collectLifeCycleFlow(
            viewModel.state,
        ) { state ->
            binding.bindWithPostsState(
                state = state,
                adapter = postsListAdapter,
            )
        }

        collectLifeCycleFlow(
            reactToPostViewModel.events,
        ) { event ->
            when (event) {
                is ReactToPostEvents.Error -> binding.root.showSnackBar(event.error)
            }
        }
        return binding.root
    }


    private fun createPostCallBacks() =
        PostClickCallBacks(
            onSendClick = { post ->
                showPostSaver(postId = post.id)
            },
            onClick = { post ->
                goToPostDetail(post.id)
            },
            onLikeClick = { post ->
                val reaction = post.currentUserReaction ?: ReactionType.Like
                reactToPost(
                    post = post,
                    reactionType = reaction,
                )
            },
            onLongClickLike = { post ->
                requireContext().showReactionDialog { reaction ->
                    reactToPost(
                        post = post,
                        reactionType = reaction,
                    )
                }
            }
        )

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

    private fun goToPostDetail(
        postId: String,
    ) = findNavController().goTo(AppRoutes.PostDetail(postId))


    private fun showPostSaver(postId: String) {
        val sheet = PostSaverSheet.newInstance(postId)
        sheet.show(parentFragmentManager, PostSaverSheet::class.simpleName)
    }
}