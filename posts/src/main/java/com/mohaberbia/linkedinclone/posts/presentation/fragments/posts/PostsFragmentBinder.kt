package com.mohaberbia.linkedinclone.posts.presentation.fragments.posts

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohaberabi.posts.databinding.FragmentPostsBinding
import com.mohaberabi.presentation.ui.util.AppRecyclerViewScrollListener
import com.mohaberabi.presentation.ui.util.extension.hideAll
import com.mohaberabi.presentation.ui.util.extension.show
import com.mohaberabi.presentation.ui.util.extension.submitIfDifferent
import com.mohaberabi.presentation.ui.util.extension.submitOnce
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
            hideAll(
                loader,
                recyclerView,
            )
            error.apply {
                show()
                setErrorTitle(state.error.asString(root.context))
            }
        }

        PostsStatus.Populated -> {
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
            recyclerView.show()
            hideAll(
                error,
                loader
            )
        }

        else -> {
            loader.show()
            hideAll(
                recyclerView,
                error,
            )
        }
    }
}
