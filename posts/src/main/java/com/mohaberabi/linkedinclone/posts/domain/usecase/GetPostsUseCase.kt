package com.mohaberabi.linkedinclone.posts.domain.usecase

import com.mohaberabi.linkedinclone.posts.domain.repository.PostsRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val postsRepository: PostsRepository,
) {

    suspend operator fun invoke() = postsRepository.getPosts()
}