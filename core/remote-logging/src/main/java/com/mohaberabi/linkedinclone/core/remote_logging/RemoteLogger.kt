package com.mohaberabi.linkedinclone.core.remote_logging

import com.mohaberabi.linkedinclone.core.remote_logging.data.FirebaseRemoteLoggingClient
import com.mohaberabi.linkedinclone.core.remote_logging.domain.RemoteLoggingClient

object RemoteLogger : RemoteLoggingClient {
    private val logger = FirebaseRemoteLoggingClient()
    override fun logInfo(
        message: String,
    ) = logger.logInfo(message)

    override fun logWarning(
        message: String,
    ) = logger.logWarning(message)

    override fun logError(
        message: String,
        throwable: Throwable?
    ) =
        logger.logError(message, throwable)

    override fun logError(
        message: String,
        error: String?
    ) = logger.logError(message, error)


    override fun logStateChange(
        root: String?,
        stateName: String,
        stateValue: String
    ) = logger.logStateChange(
        root = root,
        stateName = stateName,
        stateValue = stateValue
    )

    override fun logUserAction(
        actionName: String,
        details: Map<String, String>
    ) = logger.logUserAction(actionName, details)

    override fun logNetworkRequest(
        path: String,
        method: String,
        responseMessage: String
    ) = logger.logNetworkRequest(path, method, responseMessage)

    override fun logNetworkError(
        path: String,
        method: String,
        throwable: Throwable
    ) = logger.logNetworkError(path, method, throwable)

    override fun logCustomEvent(
        name: String,
        parameters: Map<String, String>
    ) = logger.logCustomEvent(name, parameters)
}