package com.mohaberabi.linkedin.core.domain.error


interface AppError


enum class LocalError : AppError {
    DISK_FULL,
    DATA_CORRUPTION,
}

enum class CommonError : AppError {
    IO_ERROR,
    UNKNOWN,
    ILLEGAL_STATE,
}


enum class RemoteError : AppError {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    WRONG_EMAIL_USERNAME,
    WRONG_PASSWORD,
    CONFLICT,
    TOO_MANY_REQUEST,
    NO_NETWORK,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    SERIALIZATION_ERROR,
    UNKNOWN_ERROR,
    INVALID_CREDENTIALS,
    INVALID_USER,
    EMAIL_ALREADY_IN_USE,
    USER_DISABLED,

}