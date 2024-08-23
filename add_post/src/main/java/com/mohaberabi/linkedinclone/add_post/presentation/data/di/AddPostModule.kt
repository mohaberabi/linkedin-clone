package com.mohaberabi.linkedinclone.add_post.presentation.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.mohaberabi.linkedin.core.domain.source.local.FileCompressor
import com.mohaberabi.linkedin.core.domain.source.local.FileCompressorFactory
import com.mohaberabi.linkedin.core.domain.source.local.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.StorageClient
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedinclone.add_post.presentation.data.source.remote.AddPostFirebaseRemoteDataSource
import com.mohaberabi.linkedinclone.add_post.presentation.data.source.repository.DefaultAddPostRepository
import com.mohaberabi.linkedinclone.add_post.presentation.domain.repository.AddPostRepository
import com.mohaberabi.linkedinclone.add_post.presentation.domain.source.remote.AddPostRemoteDataSource
import com.mohaberabi.linkedinclone.add_post.presentation.domain.usecase.AddPostUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)


object AddPostModule {


    @Singleton
    @Provides
    fun provideRemoteDataSource(
        fb: FirebaseFirestore,
        dispatchers: DispatchersProvider,
    ): AddPostRemoteDataSource = AddPostFirebaseRemoteDataSource(
        dispatchers = dispatchers,
        firestore = fb
    )


    @Singleton
    @Provides

    fun provideRepository(
        fileCompressor: FileCompressorFactory,
        addPostRemoteDataSource: AddPostRemoteDataSource,
        storageClient: StorageClient,
        userLocalDataSource: UserLocalDataSource,
    ): AddPostRepository = DefaultAddPostRepository(
        fileCompressor = fileCompressor,
        addPostRemoteDataSource = addPostRemoteDataSource,
        userLocalDataSource = userLocalDataSource,
        storageClient = storageClient
    )

    @Singleton
    @Provides
    fun provideUseCase(
        addPostRepository: AddPostRepository,
    ) = AddPostUseCase(addPostRepository)
}