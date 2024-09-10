package com.mohaberabi.saved_posts.data.di

import com.mohaberabi.linkedin.core.domain.repository.SavedPostsRepository
import com.mohaberabi.saved_posts.domain.usecase.GetSavedPostsWithReactionsUseCase
import com.mohaberabi.saved_posts.domain.usecase.ListenToSavedPostsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)


object SavedPostsModule {


    @Singleton
    @Provides
    fun provideListenToSavedPostsUseCase(
        repo: SavedPostsRepository,
    ) = ListenToSavedPostsUseCase(savedPostsRepository = repo)


    @Singleton
    @Provides
    fun provideGetSavedPostsUseCase(
        repo: SavedPostsRepository,
    ) = GetSavedPostsWithReactionsUseCase(savedPostsRepository = repo)
}