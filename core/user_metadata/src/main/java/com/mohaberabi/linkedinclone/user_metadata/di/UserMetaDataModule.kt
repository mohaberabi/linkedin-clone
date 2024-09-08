package com.mohaberabi.linkedinclone.user_metadata.di

import com.mohaberabi.linkedin.core.domain.repository.UserMetaDataRepository
import com.mohaberabi.linkedinclone.user_metadata.usecase.ListenToUserMetaDataUseCase
import com.mohaberabi.linkedinclone.user_metadata.usecase.MarkAllNotificationsReadUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UserMetaDataModule {


    @Singleton
    @Provides
    fun provideListenToMetaDataUseCase(
        repo: UserMetaDataRepository,
    ) = ListenToUserMetaDataUseCase(
        userMetaDataRepository = repo
    )

    @Singleton
    @Provides
    fun provideMarkAllNotiUseCase(
        repo: UserMetaDataRepository,
    ) = MarkAllNotificationsReadUseCase(
        userMetaDataRepository = repo
    )
}