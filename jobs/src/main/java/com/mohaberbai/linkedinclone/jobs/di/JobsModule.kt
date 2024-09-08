package com.mohaberbai.linkedinclone.jobs.di

import com.mohaberabi.linkedin.core.domain.repository.JobDetailRepository
import com.mohaberbai.linkedinclone.jobs.usecase.GetJobsUseCase
import com.mohaberabi.linkedin.core.domain.repository.JobRepository
import com.mohaberbai.linkedinclone.jobs.usecase.GetJobDetailsUseCase
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

    @Singleton
    @Provides
    fun provideGetJobDetailUseCase(
        jobDetailRepository: JobDetailRepository,
    ) = GetJobDetailsUseCase(
        jobDetailRepository = jobDetailRepository,
    )
}