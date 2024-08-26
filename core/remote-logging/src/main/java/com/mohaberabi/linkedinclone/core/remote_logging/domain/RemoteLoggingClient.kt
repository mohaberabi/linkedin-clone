package com.mohaberabi.linkedinclone.core.remote_logging.domain

internal interface RemoteLoggingClient {

    fun logInfo(
        message: String,
    )

    fun logWarning(
        message: String,
    )

    fun logError(
        message: String,
        throwable: Throwable? = null
    )

    fun logError(
        message: String,
        error: String? = null
    )

    fun logStateChange(
        root: String? = null,
        stateName: String,
        stateValue: String
    )

    fun logUserAction(
        actionName: String,
        details: Map<String, String> = emptyMap()
    )

    fun logNetworkRequest(
        path: String,
        method: String,
        responseMessage: String
    )

    fun logNetworkError(
        path: String,
        method: String,
        throwable: Throwable
    )

    fun logCustomEvent(
        name: String,
        parameters: Map<String, String> = emptyMap()
    )

}