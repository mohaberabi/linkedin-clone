package com.mohaberabi.linkedin.core.domain.source.remote

import com.mohaberabi.linkedin.core.domain.model.SavedPostModel
import kotlinx.coroutines.flow.Flow


interface SavedPostRemoteDataSource {
    suspend fun savePost(
        postId: String,
        uid: String,
    )


    suspend fun getSavedPosts(
        limit: Int = 20,
        lastDocId: String? = null,
        uid: String,
    ): List<SavedPostModel>
}