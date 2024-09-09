package com.mohaberabi.linkedinclone.presentation

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class LinkedInCloneApplication : Application() {
    companion object {
        private const val COIL_PATH = "coil_path"
        private const val COIL_MAX_DISK = 10 * 1024 * 1024L
    }

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
        val diskCache = DiskCache.Builder()
            .directory(cacheDir.resolve(COIL_PATH))
            .maxSizeBytes(COIL_MAX_DISK)
            .build()
        val memoryCache = MemoryCache.Builder(this)
            .maxSizePercent(0.15).build()
        val imageLoader = ImageLoader.Builder(this)
            .memoryCache { memoryCache }
            .diskCache { diskCache }
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .crossfade(500)
            .build()
        Coil.setImageLoader(imageLoader)

    }
}

