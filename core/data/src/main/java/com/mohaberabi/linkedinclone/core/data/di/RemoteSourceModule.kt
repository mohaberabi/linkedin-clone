package com.mohaberabi.linkedinclone.core.data.di

import com.mohaberabi.linkedin.core.domain.source.remote.*
import com.mohaberabi.linkedinclone.core.data.source.remote.FirebaseJobDetailRemoteDataSource
import com.mohaberabi.linkedinclone.core.data.source.remote.FirebaseJobsRemoteDataSource
import com.mohaberabi.linkedinclone.core.data.source.remote.FirebasePostsRemoteDataSource
import com.mohaberabi.linkedinclone.core.data.source.remote.FirebaseRegisterRemoteDataSource
import com.mohaberabi.linkedinclone.core.data.source.remote.FirebaseStorageStorageClient
import com.mohaberabi.linkedinclone.core.data.source.remote.FirebaseUserRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteSourceModule {

    @Binds
    @Singleton
    abstract fun bindJobDetailRemoteDataSource(
        firebaseJobDetailRemoteDataSource: FirebaseJobDetailRemoteDataSource
    ): JobDetailRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindJobsRemoteDataSource(
        firebaseJobsRemoteDataSource: FirebaseJobsRemoteDataSource
    ): JobRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindPostsRemoteDataSource(
        firebasePostsRemoteDataSource: FirebasePostsRemoteDataSource
    ): PostsRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindRegisterRemoteDataSource(
        firebaseRegisterRemoteDataSource: FirebaseRegisterRemoteDataSource
    ): RegisterRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindStorageClient(
        firebaseStorageStorageClient: FirebaseStorageStorageClient
    ): StorageClient

    @Binds
    @Singleton
    abstract fun bindUserRemoteDataSource(
        firebaseUserRemoteDataSource: FirebaseUserRemoteDataSource
    ): UserRemoteDataSource


}
