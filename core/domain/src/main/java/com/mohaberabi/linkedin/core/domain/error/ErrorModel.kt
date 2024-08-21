package com.mohaberabi.linkedin.core.domain.error


data class ErrorModel(
    val message: String? = null,
    val statusCode: Int? = null,
    val cause: Exception? = null,
    val type: AppError,
) : AppError


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

