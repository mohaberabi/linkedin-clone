package com.mohaberabi.linkedinclone.add_post.di

import com.mohaberabi.linkedinclone.add_post.usecase.AddPostUseCase
import com.mohaberabi.linkedin.core.domain.repository.JobDetailRepository
import com.mohaberabi.linkedin.core.domain.repository.PostsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AddPostModule {
    @Singleton
    @Provides
    fun provideAddPostUseCase(
        postsRepository: PostsRepository,
    ) = AddPostUseCase(
        postsRepository = postsRepository,
    )
}