package com.mohaberabi.linkedinclone.core.remote_logging.data

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.mohaberabi.linkedinclone.core.remote_logging.domain.model.LogInfo
import com.mohaberabi.linkedinclone.core.remote_logging.domain.model.RemoteLogging
import com.mohaberabi.linkedinclone.core.remote_logging.domain.model.tag
import javax.inject.Inject


class FirebaseRemoteLoggingClient @Inject constructor(
    private val crash: FirebaseCrashlytics,
) : RemoteLogging {
    override fun recordException(e: Throwable?) {
        e?.let { crash.recordException(it) }
    }

    override fun log(
        info: LogInfo
    ) = crash.log("${info.tag.tag}: ${info.extra}")
}
