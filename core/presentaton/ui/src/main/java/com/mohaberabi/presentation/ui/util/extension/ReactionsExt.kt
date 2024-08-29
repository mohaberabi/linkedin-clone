package com.mohaberabi.presentation.ui.util.extension

import com.mohaberabi.core.presentation.design_system.R
import com.mohaberabi.linkedin.core.domain.model.ReactionType


val ReactionType.label
    get() = when (this) {

        ReactionType.Clap -> R.string.clap
        ReactionType.Love -> R.string.love
        ReactionType.Like -> R.string.like
        ReactionType.Funny -> R.string.funny
        ReactionType.All -> R.string.all
    }

val ReactionType.icon
    get() = when (this) {
        ReactionType.Love -> R.drawable.love
        ReactionType.Like -> R.drawable.like
        ReactionType.Funny -> R.drawable.funny
        ReactionType.Clap -> R.drawable.clap
        ReactionType.All -> R.drawable.clap
    }

