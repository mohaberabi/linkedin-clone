package com.mohaberabi.linkedinclone.add_post.presentation.domain.source.remote


interface AddPostRemoteDataSource {
    suspend fun addPost(
        issuerBio: String,
        issuerAvatar: String,
        postData: String,
        issuerUid: String,
        issuerName: String,
        postAttachedImg: String? = null,
        postId: String,
    )
}