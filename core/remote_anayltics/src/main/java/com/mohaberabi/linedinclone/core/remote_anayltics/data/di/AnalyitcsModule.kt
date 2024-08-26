package com.mohaberabi.linedinclone.core.remote_anayltics.data.di

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.mohaberabi.linedinclone.core.remote_anayltics.data.FirebaseAppAnalytics
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AnalyitcsModule {


    @Singleton
    @Provides
    fun provideFirebaseAnalytics(
        @ApplicationContext context: Context
    ) = FirebaseAnalytics.getInstance(context).also {
        it.setAnalyticsCollectionEnabled(
            true
        )
    }

    @Singleton
    @Provides
    fun provideAnalyticsClient(
        analytics: FirebaseAnalytics,
    ): AppAnalytics =
        FirebaseAppAnalytics(
            analytics = analytics
        )
}