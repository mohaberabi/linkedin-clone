package com.mohaberabi.linkedinclone

import android.app.Application
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
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        analytics.logEvent(
            name = "appStartup",
            params = mapOf(
                "appName" to "LinkedInCloneApplication"
            )
        )
    }
}