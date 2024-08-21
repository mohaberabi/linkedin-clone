package com.mohaberabi.linkedinclone.posts.presentation.fragments.posts

import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedinclone.posts.presentation.viewmodel.PostsActions
import com.mohaberabi.linkedinclone.posts.presentation.viewmodel.PostsState
import com.mohaberabi.linkedinclone.posts.presentation.viewmodel.PostsStatus
import com.mohaberabi.posts.databinding.FragmentPostsBinding
import com.mohaberabi.posts.databinding.PostListItemBinding
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
            loader.visibility = View.GONE

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
            loader.visibility = View.GONE
            recyclerView.visibility = View.GONE
        }
    }

    private fun loading() {
        with(binding) {
            loader.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
    }


}

