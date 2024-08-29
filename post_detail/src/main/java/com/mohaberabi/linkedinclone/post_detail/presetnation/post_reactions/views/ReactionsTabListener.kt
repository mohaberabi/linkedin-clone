package com.mohaberabi.linkedinclone.post_detail.presetnation.post_reactions.views

import com.google.android.material.tabs.TabLayout
import com.mohaberabi.linkedin.core.domain.model.ReactionType

class ReactionsTabListener(
    private val onReactionSelected: (ReactionType?) -> Unit,
) : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab?) {
        val reactionType = when (tab?.position) {
            null, 0 -> null
            else -> ReactionType.entries[tab.position]
        }
        onReactionSelected(reactionType)
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }
}