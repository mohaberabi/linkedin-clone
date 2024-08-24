package com.mohaberabi.linkedinclone.job_detail.di

import com.mohaberabi.linkedinclone.job_detail.usecase.GetJobDetailsUseCase
import com.mohaberabi.linkedin.core.domain.repository.JobDetailRepository
import com.mohaberabi.linkedin.core.domain.repository.JobRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object JobDetailModule {
    @Singleton
    @Provides
    fun provideGetJobDetailUseCase(
        jobDetailRepository: JobDetailRepository,
    ) = GetJobDetailsUseCase(
        jobDetailRepository = jobDetailRepository,
    )
}