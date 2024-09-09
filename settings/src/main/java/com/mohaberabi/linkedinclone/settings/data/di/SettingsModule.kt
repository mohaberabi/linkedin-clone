package com.mohaberabi.linkedinclone.settings.data.di

import com.mohaberabi.linkedin.core.domain.source.local.user.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.AuthRemoteDataSource
import com.mohaberabi.linkedinclone.settings.data.repository.DefaultSettingsRepository
import com.mohaberabi.linkedinclone.settings.domain.repository.SettingsRepository
import com.mohaberabi.linkedinclone.settings.domain.usecase.SignOutUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)


object SettingsModule {


    @Singleton
    @Provides
    fun provideRepository(
        userLocalDataSource: UserLocalDataSource,
        authRemoteDataSource: AuthRemoteDataSource,
    ): SettingsRepository = DefaultSettingsRepository(
        authRemoteDataSource, userLocalDataSource,
    )

    @Provides
    @Singleton
    fun provideUseCase(repo: SettingsRepository) = SignOutUseCase(repo)


}