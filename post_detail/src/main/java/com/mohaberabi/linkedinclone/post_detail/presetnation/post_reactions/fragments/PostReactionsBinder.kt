package com.mohaberabi.linkedinclone.post_detail.presetnation.post_reactions.fragments

import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.mohaberabi.core.presentation.design_system.R
import com.mohaberabi.core.presentation.ui.databinding.ReactionTabViewBinding
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedinclone.post_detail.databinding.FragmentPostReactionsBinding
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_reactions.viewmodel.PostReactionsActions
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_reactions.viewmodel.PostReactionsState
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_reactions.viewmodel.PostReactionsStatus
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_reactions.views.ReactionsTabListener
import com.mohaberabi.presentation.ui.util.AppRecyclerViewScrollListener
import com.mohaberabi.presentation.ui.util.extension.hideAll
import com.mohaberabi.presentation.ui.util.extension.icon
import com.mohaberabi.presentation.ui.util.extension.show
import com.mohaberabi.presentation.ui.util.extension.submitIfDifferent
import com.mohaberabi.presentation.ui.util.extension.submitOnce
import com.mohaberabi.presentation.ui.views.post_item.showIf


fun FragmentPostReactionsBinding.setUp(
    listAdapter: ReactorsListAdapter,
    onAction: (PostReactionsActions) -> Unit,
) {
    setupTabLayout(
        onReactionSelected = { react ->
            onAction(
                PostReactionsActions.ReactionTypeChanged(react),
            )
        },
    )
    recyclerView.submitOnce(
        newLayoutManager = LinearLayoutManager(root.context),
        listAdapter = listAdapter,
        scrollListener = AppRecyclerViewScrollListener(
            onPaginate = {
                onAction(PostReactionsActions.LoadMore)
            },
            isLinear = true
        )
    )
}

private fun FragmentPostReactionsBinding.setupTabLayout(
    onReactionSelected: (ReactionType?) -> Unit,
) {
    ReactionType.entries.forEach { reaction ->
        val tab = tabLayout.newTab()
        val customTabBinding =
            ReactionTabViewBinding.inflate(LayoutInflater.from(root.context))
        customTabBinding.icon.showIf(
            reaction != ReactionType.All,
        ) {
            setImageResource(reaction.icon)
        }
        customTabBinding.label.showIf(
            reaction == ReactionType.All,
        ) {
            text = ContextCompat.getString(root.context, R.string.all)
        }
        tab.customView = customTabBinding.root
        tabLayout.addTab(tab)
    }
    tabLayout.addOnTabSelectedListener(
        ReactionsTabListener(
            onReactionSelected = onReactionSelected,
        )
    )
}


fun FragmentPostReactionsBinding.bindWithReactionsState(
    state: PostReactionsState,
    listAdapter: ReactorsListAdapter,
) {

    when (state.state) {
        PostReactionsStatus.Error -> {
            hideAll(
                loader,
                recyclerView,
            )
            errorWidget.show()
        }

        PostReactionsStatus.Populated -> {
            listAdapter.submitIfDifferent(
                state.reactions,
            )
            hideAll(
                loader,
                errorWidget,
            )
            recyclerView.show()
        }

        else -> {
            hideAll(
                errorWidget,
                recyclerView,
            )
            loader.show()

        }
    }


}