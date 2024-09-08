package com.mohaberabi.linkedin.core.domain.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow

interface SavedPostsRepository {


    suspend fun savePost(
        postId: String,
    ): EmptyDataResult<ErrorModel>

    fun listenToSavedPosts(
    ): Flow<List<PostModel>>

    suspend fun getSavedPostsWithReactions(
        limit: Int = 10,
        lastDocId: String? = null,
    ): EmptyDataResult<ErrorModel>
}