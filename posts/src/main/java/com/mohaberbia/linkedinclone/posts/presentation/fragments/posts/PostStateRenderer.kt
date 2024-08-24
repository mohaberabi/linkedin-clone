package com.mohaberbia.linkedinclone.posts.presentation.fragments.posts

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberbia.linkedinclone.posts.presentation.viewmodel.PostsActions
import com.mohaberbia.linkedinclone.posts.presentation.viewmodel.PostsState
import com.mohaberbia.linkedinclone.posts.presentation.viewmodel.PostsStatus
import com.mohaberabi.posts.databinding.FragmentPostsBinding
import com.mohaberabi.presentation.ui.util.AppRecyclerViewScrollListener

class PostStateRenderer(
    private val adapter: PostsListAdapter,
    private val binding: FragmentPostsBinding,
    private val onAction: (PostsActions) -> Unit,
) {


    fun render(state: PostsState) {
        when (state.state) {
            PostsStatus.Error -> error()
            PostsStatus.Populated -> populated(state.posts)
            else -> loading()
        }
    }

    private fun populated(items: List<PostModel>) {
        with(binding) {
            recyclerView.visibility = View.VISIBLE
            adapter.submitList(items)
            recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
            recyclerView.adapter = adapter
            loader.hide()

            recyclerView.addOnScrollListener(
                AppRecyclerViewScrollListener(true) { lastVisible, totalCount ->
                    if (lastVisible == totalCount - 1) {
                        onAction(PostsActions.LoadMore)
                    }
                },
            )
        }

    }

    private fun error() {
        with(binding) {
            loader.hide()
            recyclerView.visibility = View.GONE
        }
    }

    private fun loading() {
        with(binding) {
            loader.show()
            recyclerView.visibility = View.GONE
            error.hide()
        }
    }


}

