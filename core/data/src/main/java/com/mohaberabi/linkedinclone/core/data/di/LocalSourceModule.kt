package com.mohaberabi.linkedinclone.core.data.di

import com.google.firebase.storage.FirebaseStorage
import com.mohaberabi.linkedin.core.domain.source.local.compressor.FileCompressorFactory
import com.mohaberabi.linkedin.core.domain.source.local.persistence.PersistenceClient
import com.mohaberabi.linkedin.core.domain.source.local.user.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.StorageClient
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedinclone.core.data.source.local.compressor.DefaultFileCompressorFactory
import com.mohaberabi.linkedinclone.core.data.source.local.user.UserDataStore
import com.mohaberabi.linkedinclone.core.data.source.remote.FirebaseStorageStorageClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalSourceModule {
    @Singleton
    @Provides
    fun proivdeUserLocalDataSource(
        persistenceClient: PersistenceClient
    ): UserLocalDataSource {
        return UserDataStore(
            persistenceClient = persistenceClient
        )
    }

    @Singleton
    @Provides
    fun provideCompressorFactory(
        dispatchers: DispatchersProvider,
    ): FileCompressorFactory = DefaultFileCompressorFactory(dispatchers = dispatchers)
}