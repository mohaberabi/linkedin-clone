package com.mohaberabi.linkedin.core.domain.source.remote

import com.mohaberabi.linkedin.core.domain.model.PostModel
import kotlinx.coroutines.flow.Flow


interface PostsRemoteDataSource {
    suspend fun getPosts(
        limit: Int = 20,
        lastDocId: String? = null
    ): List<PostModel>

    suspend fun addPost(
        issuerBio: String,
        issuerAvatar: String,
        postData: String,
        issuerUid: String,
        issuerName: String,
        postAttachedImg: String? = null,
        postId: String,
    )

    suspend fun getPostsByIds(
        postsIds: List<String>,
    ): List<PostModel>


    fun listenToPost(
        postId: String,
        uid: String,
    ): Flow<PostModel?>
}