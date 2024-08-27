package com.mohaberbia.linkedinclone.posts.presentation.fragments.posts

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohaberabi.posts.databinding.FragmentPostsBinding
import com.mohaberabi.presentation.ui.util.AppRecyclerViewScrollListener
import com.mohaberabi.presentation.ui.util.submitIfDifferent
import com.mohaberabi.presentation.ui.util.submitOnce
import com.mohaberbia.linkedinclone.posts.presentation.viewmodel.PostsActions
import com.mohaberbia.linkedinclone.posts.presentation.viewmodel.PostsState
import com.mohaberbia.linkedinclone.posts.presentation.viewmodel.PostsStatus

fun FragmentPostsBinding.bind(
    state: PostsState,
    adapter: PostsListAdapter,
    onAction: (PostsActions) -> Unit,
) {

    recyclerView.submitOnce(
        listAdapter = adapter,
        newLayoutManager = LinearLayoutManager(root.context),
        scrollListener = AppRecyclerViewScrollListener(
            isLinear = true,
        ) {
            onAction(PostsActions.LoadMore)
        }
    )

    adapter.submitIfDifferent(
        state.posts,
    )
    when (state.state) {
        PostsStatus.Error -> {
            loader.hide()
            error.show()
            error.setErrorTitle(state.error.asString(root.context))
            recyclerView.visibility = View.GONE
        }

        PostsStatus.Populated -> {
            recyclerView.visibility = View.VISIBLE
            error.hide()
            loader.hide()

        }

        else -> {
            loader.show()
            recyclerView.visibility = View.GONE
            error.hide()
        }
    }
}
