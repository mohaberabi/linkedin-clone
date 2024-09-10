package com.mohaberbia.linkedinclone.posts.domain

import com.mohaberabi.linedinclone.core.remote_anayltics.domain.LogEventUseCase


internal fun LogEventUseCase.logPostsGoInterest() = this("postsGotInterests")