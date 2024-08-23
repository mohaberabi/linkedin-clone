package com.mohaberabi.linkedinclone.add_post.presentation.domain.usecase

import com.mohaberabi.linkedin.core.domain.model.AppFile
import com.mohaberabi.linkedinclone.add_post.presentation.domain.repository.AddPostRepository
import javax.inject.Inject


class AddPostUseCase @Inject constructor(
    private val addPostRepository: AddPostRepository
) {
    suspend operator fun invoke(
        postData: String,
        postAttachedImg: AppFile? = null,
        postId: String,
    ) = addPostRepository.addPost(
        postData = postData,
        postAttachedImg = postAttachedImg,
        postId = postId
    )
}


