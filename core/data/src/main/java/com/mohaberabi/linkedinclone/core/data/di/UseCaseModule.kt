package com.mohaberabi.linkedinclone.core.data.di

import com.mohaberabi.linkedin.core.domain.repository.PostsReactionRepository
import com.mohaberabi.linkedin.core.domain.repository.UserRepository
import com.mohaberabi.linkedin.core.domain.usecase.user.GetUserUseCase
import com.mohaberabi.linkedin.core.domain.usecase.user.ListenToCurrentUserUseCase
import com.mohaberabi.linkedin.core.domain.usecase.reaction.PostReactionHandler
import com.mohaberabi.linkedin.core.domain.usecase.reaction.ReactToPostUseCase
import com.mohaberabi.linkedin.core.domain.usecase.reaction.UndoReactToPostUseCase
import com.mohaberabi.linkedin.core.domain.usecase.validator.ValidatorUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {


    @Singleton
    @Provides
    fun provideGetUserUseCase(
        userRepository: UserRepository
    ): GetUserUseCase {
        return GetUserUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideListenToUserUseCase(
        userRepository: UserRepository
    ): ListenToCurrentUserUseCase {
        return ListenToCurrentUserUseCase(userRepository)
    }

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

    @Singleton
    @Provides
    fun providePostReactionHandler(
    ): PostReactionHandler {
        return PostReactionHandler()
    }

    @Singleton
    @Provides
    fun provideValidaotrUseCase(
    ): ValidatorUseCases {
        return ValidatorUseCases()
    }

}