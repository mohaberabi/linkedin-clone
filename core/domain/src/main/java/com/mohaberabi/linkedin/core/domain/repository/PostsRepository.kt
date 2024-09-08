package com.mohaberabi.linkedin.core.domain.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.AppFile
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow

interface PostsRepository {


    suspend fun getPostsWithUserReaction(
        limit: Int = 20,
        lastDocId: String? = null,
    ): EmptyDataResult<ErrorModel>

    suspend fun addPost(
        postData: String,
        postAttachedImg: AppFile? = null,
        postId: String,
    ): EmptyDataResult<ErrorModel>

    fun listenToPosts(
    ): Flow<List<PostModel>>
}