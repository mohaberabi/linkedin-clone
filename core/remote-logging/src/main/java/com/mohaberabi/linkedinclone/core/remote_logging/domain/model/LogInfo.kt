package com.mohaberabi.linkedinclone.core.remote_logging.domain.model


sealed class LogInfo(
    val extra: String,
) {
    data class Info(val message: String = "") : LogInfo(message)
    data class Error(
        val errorMessage: String? = null,
        val error: String? = null,
        val cause: Throwable? = null
    ) : LogInfo("ErrorMessage: $errorMessage, Error: $error, Cause: $cause")

    data class StateChanged(
        val stateName: String = "",
        val value: String = "",
        val root: String = ""
    ) : LogInfo("Name: $stateName, VALUE: $value, Root: $root")

    data class Action(
        val params: Map<String, String> = mapOf(),
        val action: String,
    ) : LogInfo("Action: $action, Params: ${params.toLogMessage()}")

    data class NetworkRequest(
        val path: String = "",
        val method: String = "",
        val responseMessage: String? = null,
    ) : LogInfo("Path: $path, Method: $method, Response: $responseMessage")

    data class NetworkError(
        val path: String = "",
        val method: String = "",
        val error: Throwable? = null,
    ) : LogInfo("Path: $path, Method: $method, Error: ${error?.message}")

    data class Custom(
        val message: String = "",
        val params: Map<String, String> = mapOf(),
    ) : LogInfo("Message: $message, Params: ${params.toLogMessage()}")

}

val LogInfo.tag: LogTag
    get() = when (this) {
        is LogInfo.Action -> LogTag.USER_ACTION
        is LogInfo.Custom -> LogTag.CUSTOM_EVENT
        is LogInfo.Error -> LogTag.ERROR
        is LogInfo.Info -> LogTag.INFO
        is LogInfo.NetworkError -> LogTag.NETWORK_ERROR
        is LogInfo.NetworkRequest -> LogTag.REQUEST
        is LogInfo.StateChanged -> LogTag.STATE_CHANGE
    }

internal fun Map<String, String>.toLogMessage() =
    entries.joinToString(separator = ", ") { "Key: ${it.key}, VALUE: ${it.value}" }