package com.mohaberabi.linkedin.core.domain.source.local.posts

import com.mohaberabi.linkedin.core.domain.model.PostModel
import kotlinx.coroutines.flow.Flow

interface PostsLocalDataSource {
    suspend fun upsertPost(post: PostModel)
    suspend fun upsertPosts(posts: List<PostModel>)
    fun getPosts(): Flow<List<PostModel>>
    fun getPostById(postId: String): Flow<PostModel?>
    suspend fun reactToPost(postId: String, reaction: String)
    suspend fun undoReaction(postId: String)
}