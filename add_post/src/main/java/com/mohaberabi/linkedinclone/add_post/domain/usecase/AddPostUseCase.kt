package com.mohaberabi.linkedinclone.add_post.domain.usecase

import com.mohaberabi.linkedin.core.domain.model.AppFile
import com.mohaberabi.linkedin.core.domain.repository.PostsRepository
import javax.inject.Inject


class AddPostUseCase @Inject constructor(
    private val postsRepository: PostsRepository
) {
    suspend operator fun invoke(
        postData: String,
        postAttachedImg: AppFile? = null,
        postId: String,
    ) = postsRepository.addPost(
        postData = postData,
        postAttachedImg = postAttachedImg,
        postId = postId
    )
}


