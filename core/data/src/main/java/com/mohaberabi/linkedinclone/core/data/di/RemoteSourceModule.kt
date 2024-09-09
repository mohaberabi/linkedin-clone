package com.mohaberabi.linkedinclone.core.data.di

import com.mohaberabi.linkedin.core.domain.source.remote.*
import com.mohaberabi.linkedinclone.core.data.source.remote.FirebaseInAppNotificationsRemoteDataSource
import com.mohaberabi.linkedinclone.core.data.source.remote.FirebaseJobDetailRemoteDataSource
import com.mohaberabi.linkedinclone.core.data.source.remote.FirebaseJobsRemoteDataSource
import com.mohaberabi.linkedinclone.core.data.source.remote.FirebasePostReactionsRemoteDataSource
import com.mohaberabi.linkedinclone.core.data.source.remote.FirebasePostsRemoteDataSource
import com.mohaberabi.linkedinclone.core.data.source.remote.FirebaseProfileViewsRemoteDataSource
import com.mohaberabi.linkedinclone.core.data.source.remote.FirebaseAuthRemoteDataSource
import com.mohaberabi.linkedinclone.core.data.source.remote.FirebaseSavedPostsRemoteDataSource
import com.mohaberabi.linkedinclone.core.data.source.remote.FirebaseStorageClient
import com.mohaberabi.linkedinclone.core.data.source.remote.FirebaseUserMetaDataRemoteDataSource
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
        firebaseRegisterRemoteDataSource: FirebaseAuthRemoteDataSource
    ): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindStorageClient(
        firebaseStorageStorageClient: FirebaseStorageClient
    ): StorageClient

    @Binds
    @Singleton
    abstract fun bindUserRemoteDataSource(
        firebaseUserRemoteDataSource: FirebaseUserRemoteDataSource
    ): UserRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindPostReactionsRemoteDataSource(
        firebasePostReactionsRemoteDataSource: FirebasePostReactionsRemoteDataSource
    ): PostReactionsRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindInAppNotificationsRemoteDataSource(
        firebaseInAppNotificationsRemoteDataSource: FirebaseInAppNotificationsRemoteDataSource
    ): InAppNotificationsRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindUserMetaDataRemoteDataSource(
        firebaseUserMetaDataRemoteDataSource: FirebaseUserMetaDataRemoteDataSource
    ): UserMetaDataRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindSavedPostRemoteSource(
        firebaseSavedPostsRemoteDataSource: FirebaseSavedPostsRemoteDataSource
    ): SavedPostRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindProfileViewsRemoteDataSource(
        firebaseProfileViewsRemoteDataSource: FirebaseProfileViewsRemoteDataSource
    ): ProfileViewsRemoteDataSource
}
