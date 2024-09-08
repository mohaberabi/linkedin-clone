package com.mohaberabi.linkedinclone.profile_views.data.di

import com.mohaberabi.linkedin.core.domain.repository.ProfileViewsRepository
import com.mohaberabi.linkedinclone.profile_views.domain.usecase.GetProfileViewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProfileViewsModule {


    @Provides
    @Singleton
    fun provideGetProfileViewsUseCase(
        repo: ProfileViewsRepository,
    ) = GetProfileViewsUseCase(repo)

}