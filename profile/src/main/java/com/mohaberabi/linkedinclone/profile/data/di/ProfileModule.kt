package com.mohaberabi.linkedinclone.profile.data.di

import com.mohaberabi.linkedin.core.domain.repository.ProfileViewsRepository
import com.mohaberabi.linkedinclone.profile.domain.usecase.ViewSomeoneProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)


object ProfileModule {
    @Provides
    @Singleton
    fun provideViewSomoneProfileUseCase(
        repo: ProfileViewsRepository,
    ) = ViewSomeoneProfileUseCase(
        profileViewsRepository = repo,
    )
}