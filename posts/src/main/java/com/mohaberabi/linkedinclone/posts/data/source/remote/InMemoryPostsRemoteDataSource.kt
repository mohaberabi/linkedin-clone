package com.mohaberabi.linkedinclone.posts.data.source.remote

import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedinclone.posts.domain.source.remote.PostsRemoteDataSource
import javax.inject.Inject


class InMemoryPostsRemoteDataSource @Inject constructor() : PostsRemoteDataSource {
    override suspend fun getPosts(): List<PostModel> {
        return buildList {
            repeat(20) {
                add(
                    PostModel(
                        id = "$it",
                        createdAtMillis = System.currentTimeMillis() - 3600000,
                        issuerName = "Mohab Erabi",
                        issuerUid = "user_001",
                        issuerAvatar = "https://gratisography.com/wp-content/uploads/2024/01/gratisography-cyber-kitty-800x525.jpg",
                        issuerBio = "Software Engineer at LinkedIn",
                        postData = "This is a sample post content by John Doe.",
                        commentsCount = 10,
                        reactionsCount = 150,
                        repostsCount = 20,
                        postAttachedImg = if (it % 2 == 0) "https://gratisography.com/wp-content/uploads/2024/01/gratisography-cyber-kitty-800x525.jpg" else ""
                    )
                )

            }
        }
    }
}