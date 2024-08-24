package com.mohaberabi.linkedin.core.domain.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.AppFile
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult

interface PostsRepository {


    suspend fun getPosts(
        limit: Int = 20,
        lastDocId: String? = null,
    ): AppResult<List<PostModel>, ErrorModel>

    suspend fun addPost(
        postData: String,
        postAttachedImg: AppFile? = null,
        postId: String,
    ): EmptyDataResult<ErrorModel>
}