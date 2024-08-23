package com.mohaberabi.linkedinclone

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()
}