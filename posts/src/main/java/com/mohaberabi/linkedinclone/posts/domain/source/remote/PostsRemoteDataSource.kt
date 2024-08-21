package com.mohaberabi.linkedinclone.posts.domain.source.remote

import com.mohaberabi.linkedin.core.domain.model.PostModel


interface PostsRemoteDataSource {
    suspend fun getPosts(): List<PostModel>
}