package com.mohaberbia.linkedinclone.posts.presentation.viewmodel

import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.model.ReactionModel
import com.mohaberabi.linkedinclone.core.remote_logging.RemoteLogger
import com.mohaberabi.presentation.ui.util.UiText


enum class PostsStatus {
    Initial,
    Loading,
    Error,
    Populated
}

data class PostsState(
    val state: PostsStatus = PostsStatus.Initial,
    val posts: List<PostModel> = listOf(),
    val error: UiText = UiText.Empty,
)


fun PostsState.logRemotely() = RemoteLogger.logStateChange(
    root = PostsViewModel::class.simpleName,
    stateValue = toString(),
    stateName = PostsState::class.java.name
)