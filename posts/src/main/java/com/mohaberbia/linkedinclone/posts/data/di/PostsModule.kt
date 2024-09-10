package com.mohaberbia.linkedinclone.posts.data.di

import com.mohaberbia.linkedinclone.posts.domain.usecase.GetPostsUseCase
import com.mohaberabi.linkedin.core.domain.repository.PostsRepository
import com.mohaberbia.linkedinclone.posts.domain.usecase.ListenToPostsUseCase
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

    @Singleton
    @Provides

    fun listenToPostsUseCase(
        postsRepository: PostsRepository,
    ) = ListenToPostsUseCase(
        postsRepository = postsRepository,
    )
}