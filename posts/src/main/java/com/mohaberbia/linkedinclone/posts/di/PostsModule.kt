package com.mohaberbia.linkedinclone.posts.di

import com.mohaberbia.linkedinclone.posts.usecase.GetPostsUseCase
import com.mohaberabi.linkedin.core.domain.repository.PostsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PostsModule {

    @Singleton
    @Provides

    fun provideGetPostsUseCase(
        postsRepository: PostsRepository,
    ) = GetPostsUseCase(
        postsRepository = postsRepository,
    )
}