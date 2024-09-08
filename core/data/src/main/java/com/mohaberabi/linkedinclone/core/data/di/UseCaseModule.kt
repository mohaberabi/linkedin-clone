package com.mohaberabi.linkedinclone.core.data.di

import com.mohaberabi.linkedin.core.domain.repository.InAppNotificationsRepository
import com.mohaberabi.linkedin.core.domain.repository.UserRepository
import com.mohaberabi.linkedin.core.domain.usecase.in_app_noti.AddInAppNotificationUseCase
import com.mohaberabi.linkedin.core.domain.usecase.user.ListenToUserUseCase
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
    fun provideListenToUserUseCase(
        userRepository: UserRepository
    ): ListenToUserUseCase {
        return ListenToUserUseCase(userRepository)
    }


    @Singleton
    @Provides
    fun provideValidaotrUseCase(
    ): ValidatorUseCases {
        return ValidatorUseCases()
    }

    @Singleton
    @Provides
    fun provideAddInAppNotiUseCase(
        repo: InAppNotificationsRepository
    ): AddInAppNotificationUseCase {
        return AddInAppNotificationUseCase(repo)
    }

}