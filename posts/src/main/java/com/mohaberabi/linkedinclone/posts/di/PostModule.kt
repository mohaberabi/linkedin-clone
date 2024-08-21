package com.mohaberabi.linkedinclone.posts.di

import com.mohaberabi.linkedinclone.posts.data.repository.DefaultPostsRepository
import com.mohaberabi.linkedinclone.posts.data.source.remote.InMemoryPostsRemoteDataSource
import com.mohaberabi.linkedinclone.posts.domain.repository.PostsRepository
import com.mohaberabi.linkedinclone.posts.domain.source.remote.PostsRemoteDataSource
import com.mohaberabi.linkedinclone.posts.domain.usecase.GetPostsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object PostModule {
    @Singleton
    @Provides
    fun providePostRemoteDataSource(
    ): PostsRemoteDataSource = InMemoryPostsRemoteDataSource()

    @Singleton
    @Provides
    fun providePostsRepository(
        remoteDataSource: PostsRemoteDataSource
    ): PostsRepository = DefaultPostsRepository(remoteDataSource)


    @Singleton
    @Provides
    fun provideGetPostsUseCase(
        postsRepository: PostsRepository,
    ) = GetPostsUseCase(postsRepository)
}