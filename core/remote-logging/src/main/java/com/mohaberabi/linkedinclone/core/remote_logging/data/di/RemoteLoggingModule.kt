package com.mohaberabi.linkedinclone.core.remote_logging.data.di

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.mohaberabi.linkedinclone.core.remote_logging.data.FirebaseRemoteLoggingClient
import com.mohaberabi.linkedinclone.core.remote_logging.domain.RemoteLogging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RemoteLoggingModule {


    @Singleton
    @Provides
    fun provideCrash() = FirebaseCrashlytics.getInstance()

    @Provides
    @Singleton
    fun provideRemoteLogging(
        crash: FirebaseCrashlytics,
    ): RemoteLogging = FirebaseRemoteLoggingClient(
        crash
    )
}