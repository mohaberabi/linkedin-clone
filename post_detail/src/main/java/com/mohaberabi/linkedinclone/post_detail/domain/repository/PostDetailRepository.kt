package com.mohaberabi.linkedinclone.post_detail.domain.repository

import com.mohaberabi.linkedin.core.domain.model.PostDetailModel
import kotlinx.coroutines.flow.Flow


interface PostDetailRepository {

    fun listenToPostDetails(
        postId: String,
    ): Flow<PostDetailModel>
}