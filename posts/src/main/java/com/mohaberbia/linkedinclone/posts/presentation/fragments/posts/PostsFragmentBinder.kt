package com.mohaberbia.linkedinclone.posts.presentation.fragments.posts

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohaberabi.posts.databinding.FragmentPostsBinding
import com.mohaberabi.presentation.ui.util.AppRecyclerViewScrollListener
import com.mohaberbia.linkedinclone.posts.presentation.viewmodel.PostsActions
import com.mohaberbia.linkedinclone.posts.presentation.viewmodel.PostsState
import com.mohaberbia.linkedinclone.posts.presentation.viewmodel.PostsStatus

fun FragmentPostsBinding.bind(
    state: PostsState,
    adapter: PostsListAdapter,
    onAction: (PostsActions) -> Unit,
) {
    when (state.state) {
        PostsStatus.Error -> {
            pullRefresh.isRefreshing = false
            loader.hide()
            error.show()
            error.setErrorTitle(state.error.asString(root.context))
        }

        PostsStatus.Populated -> {
            pullRefresh.isRefreshing = false
            pullRefresh.setOnRefreshListener {
                onAction(PostsActions.Refresh)
            }
            recyclerView.visibility = View.VISIBLE
            error.hide()
            adapter.submitList(state.posts)
            recyclerView.layoutManager = LinearLayoutManager(root.context)
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

        else -> {
            loader.show()
            recyclerView.visibility = View.GONE
            error.hide()
        }
    }
}
