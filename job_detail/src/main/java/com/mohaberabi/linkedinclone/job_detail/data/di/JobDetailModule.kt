package com.mohaberabi.linkedinclone.job_detail.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedinclone.job_detail.data.repository.DefaultJobDetailRepository
import com.mohaberabi.linkedinclone.job_detail.data.source.remote.FirebaseJobDetailRemoteDataSource
import com.mohaberabi.linkedinclone.job_detail.domain.repository.JobDetailRepository
import com.mohaberabi.linkedinclone.job_detail.domain.source.remote.JobDetailRemoteDataSource
import com.mohaberabi.linkedinclone.job_detail.domain.usecase.GetJobDetailsUseCase
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
    fun provideRemoteDataSource(
        fb: FirebaseFirestore,
        dispatcher: DispatchersProvider
    ): JobDetailRemoteDataSource = FirebaseJobDetailRemoteDataSource(fb, dispatcher)

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: JobDetailRemoteDataSource,
    ): JobDetailRepository = DefaultJobDetailRepository(remoteDataSource)


    @Singleton
    @Provides
    fun provideUseCase(
        repo: JobDetailRepository,
    ) = GetJobDetailsUseCase(repo)
}