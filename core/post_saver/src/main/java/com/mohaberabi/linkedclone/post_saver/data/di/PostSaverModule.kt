package com.mohaberabi.linkedclone.post_saver.data.di

import com.mohaberabi.linkedclone.post_saver.domain.usecase.SavePostUseCase
import com.mohaberabi.linkedin.core.domain.repository.SavedPostsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PostSaverModule {


    @Singleton
    @Provides
    fun provideUseCase(repo: SavedPostsRepository) = SavePostUseCase(repo)
}