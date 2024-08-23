package com.mohaberabi.linkedinclone

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mohaberabi.linkedin.core.domain.source.remote.StorageClient
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedinclone.firebase_stroage.FirebaseStorageStorageClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)


object FirestoreModule {


    @Provides
    @Singleton
    fun providerFirebaseFirestore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStorage(
    ) = FirebaseStorage.getInstance()


    @Provides
    @Singleton
    fun provideFirebaseStorageClient(
        dispatchers: DispatchersProvider,
        storage: FirebaseStorage,
    ): StorageClient = FirebaseStorageStorageClient(
        dispatchers = dispatchers,
        storage = storage
    )

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()
}