package com.mohaberabi.linkedinclone.post_reactions.data.di

import com.mohaberabi.linkedin.core.domain.repository.PostsReactionRepository
import com.mohaberabi.linkedinclone.post_reactions.domain.usecase.ReactToPostUseCase
import com.mohaberabi.linkedinclone.post_reactions.domain.usecase.UndoReactToPostUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)


object ReactToPostModule {

    @Singleton
    @Provides
    fun provideReactToPostUseCase(
        postPostsReactionRepository: PostsReactionRepository,
    ): ReactToPostUseCase {
        return ReactToPostUseCase(postPostsReactionRepository)
    }

    @Singleton
    @Provides
    fun provideUndoReactToPostUseCase(
        postPostsReactionRepository: PostsReactionRepository,
    ): UndoReactToPostUseCase {
        return UndoReactToPostUseCase(postPostsReactionRepository)
    }


}