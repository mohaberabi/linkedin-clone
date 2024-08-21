package com.mohaberabi.linkedinclone.posts.data.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedinclone.posts.domain.repository.PostsRepository
import com.mohaberabi.linkedinclone.posts.domain.source.remote.PostsRemoteDataSource
import javax.inject.Inject

class DefaultPostsRepository @Inject constructor(
    private val postRemoteDataSource: PostsRemoteDataSource,
) : PostsRepository {
    override suspend fun getPosts(): AppResult<List<PostModel>, ErrorModel> {
        return AppResult.handle {
            postRemoteDataSource.getPosts()
        }
    }
}