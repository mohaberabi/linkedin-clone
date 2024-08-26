package com.mohaberabi.linedinclone.core.remote_anayltics.data

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.mohaberabi.linedinclone.core.remote_anayltics.domain.AppAnalytics
import javax.inject.Inject


class FirebaseAppAnalytics @Inject constructor(
    private val analytics: FirebaseAnalytics
) : AppAnalytics {
    override fun logEvent(name: String, params: Map<String, String>) {
        analytics.logEvent(
            name,
            Bundle().apply {
                params.forEach {
                    putString(it.key, it.value)
                }
            }
        )
    }

    override fun setUserId(id: String) {
        analytics.setUserId(id)
    }


}