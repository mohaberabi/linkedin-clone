package com.mohaberbai.linkedinclone.jobs.di

import com.mohaberbai.linkedinclone.jobs.usecase.GetJobsUseCase
import com.mohaberabi.linkedin.core.domain.repository.JobRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object JobsModule {
    @Singleton
    @Provides
    fun provideGetJobsUseCae(
        jobRepository: JobRepository,
    ) = GetJobsUseCase(
        jobRepository = jobRepository,
    )
}