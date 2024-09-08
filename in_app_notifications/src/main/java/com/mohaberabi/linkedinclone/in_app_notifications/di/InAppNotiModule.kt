package com.mohaberabi.linkedinclone.in_app_notifications.di

import com.mohaberabi.linkedin.core.domain.repository.InAppNotificationsRepository
import com.mohaberabi.linkedinclone.in_app_notifications.domain.usecase.GetInAppNotificationsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object InAppNotiModule {


    @Provides
    @Singleton
    fun provideUseCase(
        repo: InAppNotificationsRepository
    ) = GetInAppNotificationsUseCase(inAppNotificationsRepository = repo)
}