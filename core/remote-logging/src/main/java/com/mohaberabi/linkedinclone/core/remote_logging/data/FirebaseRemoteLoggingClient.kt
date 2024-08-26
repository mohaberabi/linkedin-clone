package com.mohaberabi.linkedinclone.core.remote_logging.data

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.mohaberabi.linkedinclone.core.remote_logging.domain.RemoteLoggingClient


internal class FirebaseRemoteLoggingClient(
    private val crash: FirebaseCrashlytics = FirebaseCrashlytics.getInstance()
) : RemoteLoggingClient {

    companion object {
        const val ERROR = "Error"
        const val INFO = "Info"
        const val WARN = "Warning"
        const val STATE_CHANGE = "State Changed"
        const val USER_ACT = "State Changed"
        const val CUSTOM_EVENT = "Custom  Event"
        const val REQUEST = "Network Request"
        const val NETWORK_ERROR = "NETWORK_ERROR"

    }

    override fun logInfo(message: String) = crash.log("${INFO}-${message}")

    override fun logWarning(message: String) = crash.log("${WARN}-$message")

    override fun logError(message: String, throwable: Throwable?) {
        throwable?.let {
            crash.recordException(throwable)
        } ?: crash.log(message)
    }

    override fun logError(message: String, error: String?) =
        crash.log("${ERROR}-${message}-${error}")

    override fun logStateChange(root: String?, stateName: String, stateValue: String) {
        crash.log("${STATE_CHANGE}- Name:${stateName} , VALUE:${stateValue} , Root:${root}")
    }

    override fun logUserAction(actionName: String, details: Map<String, String>) {
        val message = details.forEach {
            buildString {
                append("Key:${it.key}-")
                append("VALUE:${it.value}-")
            }
        }
        crash.log("$USER_ACT , $message")


    }

    override fun logNetworkRequest(
        path: String,
        method: String,
        responseMessage: String
    ) {
        crash.log("$REQUEST - Path:${path} , Method:${method} responseMssage:${responseMessage}")
    }

    override fun logNetworkError(
        path: String,
        method: String,
        throwable: Throwable
    ) {
        crash.log("$NETWORK_ERROR - Path:${path} , Method:${method} error:${throwable.toString()}")
        crash.recordException(throwable)
    }

    override fun logCustomEvent(name: String, parameters: Map<String, String>) {
        crash.log("${CUSTOM_EVENT}: $name - $parameters")
    }

}