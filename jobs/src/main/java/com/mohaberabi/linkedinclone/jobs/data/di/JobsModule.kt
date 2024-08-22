package com.mohaberabi.linkedinclone.jobs.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedinclone.jobs.data.repository.DefaultJobRepository
import com.mohaberabi.linkedinclone.jobs.data.soruce.remote.FirebaseJobsRemoteDataSource
import com.mohaberabi.linkedinclone.jobs.domain.repository.JobRepository
import com.mohaberabi.linkedinclone.jobs.domain.source.JobRemoteDataSource
import com.mohaberabi.linkedinclone.jobs.domain.usecase.GetJobsUseCase
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
    fun provideRemoteDataSource(
        firestore: FirebaseFirestore,
        dispatchers: DispatchersProvider
    ): JobRemoteDataSource = FirebaseJobsRemoteDataSource(
        firestore = firestore,
        dispatchers = dispatchers
    )

    @Singleton
    @Provides
    fun provideJobsRepository(
        remoteDataSource: JobRemoteDataSource,
    ): JobRepository = DefaultJobRepository(
        jobRemoteDataSource = remoteDataSource,
    )

    @Singleton
    @Provides
    fun provideGetJobsUseCase(
        jobRepository: JobRepository,
    ): GetJobsUseCase = GetJobsUseCase(
        jobRepository = jobRepository,
    )


}