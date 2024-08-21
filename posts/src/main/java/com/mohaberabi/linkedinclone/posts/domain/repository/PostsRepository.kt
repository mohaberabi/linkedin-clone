package com.mohaberabi.linkedinclone.posts.domain.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.util.AppResult

interface PostsRepository {


    suspend fun getPosts(): AppResult<List<PostModel>, ErrorModel>
}