package com.mohaberabi.linkedin.core.domain.error


data class ErrorModel(
    val message: String? = null,
    val statusCode: Int? = null,
    val cause: Exception? = null,
    val type: AppError,
) : AppError {

    companion object {
        const val TAG = "appError"
    }

    override fun toString(): String {
        val message = buildString {
            append("App Error ->")
            append("Type ${type}- ")
            append("Message ${message}- ")
            append("Code ${statusCode}- ")
        }
        return message
    }
}


sealed class AppException(
    val error: ErrorModel,
) : Exception(error.message) {
    class RemoteException(
        error: ErrorModel,
    ) : AppException(error)

    class LocalException(
        error: ErrorModel,
    ) : AppException(error)

    class CommonException(
        error: ErrorModel,
    ) : AppException(error)
}

