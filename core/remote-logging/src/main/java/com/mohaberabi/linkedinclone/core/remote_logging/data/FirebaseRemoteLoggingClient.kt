package com.mohaberabi.linkedinclone.core.remote_logging.data

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.mohaberabi.linkedinclone.core.remote_logging.domain.RemoteLogging
import javax.inject.Inject

enum class LogTag(val tag: String) {
    ERROR("Error"),
    INFO("Info"),
    WARN("Warning"),
    STATE_CHANGE("State Changed"),
    USER_ACTION("User Action"),
    CUSTOM_EVENT("Custom Event"),
    REQUEST("Network Request"),
    NETWORK_ERROR("Network Error");
}


class FirebaseRemoteLoggingClient @Inject constructor(
    private val crash: FirebaseCrashlytics,
) : RemoteLogging {

    override fun logInfo(message: String) = log(LogTag.INFO, message)

    override fun logWarning(message: String) = log(LogTag.WARN, message)

    override fun logError(message: String, throwable: Throwable?) {
        throwable?.let {
            crash.recordException(it)
        }
        log(LogTag.ERROR, message)
    }

    override fun logError(
        message: String,
        error: String?
    ) {
        log(LogTag.ERROR, "$message - $error")
    }

    override fun logStateChange(
        root: String?,
        stateName: String,
        stateValue: String
    ) {
        log(LogTag.STATE_CHANGE, "Name: $stateName, VALUE: $stateValue, Root: $root")
    }

    override fun logUserAction(
        actionName: String,
        details: Map<String, String>
    ) {
        val detailsString =
            details.entries.joinToString(separator = ", ") { "Key: ${it.key}, VALUE: ${it.value}" }
        log(LogTag.USER_ACTION, "Action: $actionName, Details: $detailsString")
    }

    override fun logNetworkRequest(
        path: String,
        method: String, responseMessage: String
    ) {
        log(LogTag.REQUEST, "Path: $path, Method: $method, Response: $responseMessage")
    }

    override fun logNetworkError(
        path: String,
        method: String, throwable: Throwable
    ) {
        log(LogTag.NETWORK_ERROR, "Path: $path, Method: $method, Error: ${throwable.message}")
        crash.recordException(throwable)
    }

    override fun logCustomEvent(
        name: String,
        parameters: Map<String, String>
    ) {
        val parametersString =
            parameters.entries.joinToString(separator = ", ") { "Key: ${it.key}, VALUE: ${it.value}" }
        log(LogTag.CUSTOM_EVENT, "Event: $name, Parameters: $parametersString")
    }

    private fun log(tag: LogTag, message: String) {
        crash.log("${tag.tag}: $message")
    }
}
