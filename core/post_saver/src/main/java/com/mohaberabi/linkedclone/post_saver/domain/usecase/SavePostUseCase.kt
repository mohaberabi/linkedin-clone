package com.mohaberabi.linkedclone.post_saver.domain.usecase

import com.mohaberabi.linkedin.core.domain.repository.SavedPostsRepository

class SavePostUseCase(
    private val savedPostsRepository: SavedPostsRepository
) {


    suspend operator fun invoke(id: String) = savedPostsRepository.savePost(id)
}