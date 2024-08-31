package com.mohaberabi.linkedinclone

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.memory.MemoryCache
import coil.request.CachePolicy
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics
import com.mohaberabi.linkedinclone.core.remote_logging.RemoteLogger
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class LinkedInCloneApplication : Application() {
    @Inject
    lateinit var analytics: AppAnalytics
    override fun onCreate() {
        super.onCreate()
        setupCoil()
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        analytics.logEvent(
            name = "appStartup",
            params = mapOf(
                "appName" to "LinkedInCloneApplication"
            )
        )
    }

    private fun setupCoil() {
        val memoryCache = MemoryCache.Builder(this)
            .maxSizePercent(0.25).build()
        val imageLoader = ImageLoader.Builder(this)
            .crossfade(true)
            .memoryCache { memoryCache }
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()
        Coil.setImageLoader(imageLoader)

    }
}

