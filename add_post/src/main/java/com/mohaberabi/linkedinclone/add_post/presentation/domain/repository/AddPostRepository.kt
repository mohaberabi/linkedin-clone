package com.mohaberabi.linkedinclone.add_post.presentation.domain.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.AppFile
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult


interface AddPostRepository {
    suspend fun addPost(
        postData: String,
        postAttachedImg: AppFile? = null,
        postId: String,
    ): EmptyDataResult<ErrorModel>
}