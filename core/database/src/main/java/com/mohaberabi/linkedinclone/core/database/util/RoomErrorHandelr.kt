package com.mohaberabi.linkedinclone.core.database.util


import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteFullException
import android.util.Log
import com.mohaberabi.linkedin.core.domain.error.AppException
import com.mohaberabi.linkedin.core.domain.error.CommonError
import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.error.LocalError
import kotlinx.coroutines.CancellationException
import java.io.IOException

internal suspend fun <T> safeCall(
    action: suspend () -> T,
): T {
    return try {
        action()
    } catch (e: Exception) {
        e.printStackTrace()
        throw AppException.LocalException(e.handleRoomError())
    }
}

internal fun Exception.handleRoomError(): ErrorModel {
    val error = when (this) {
        is CancellationException -> throw this
        is IOException -> ErrorModel(
            type = CommonError.IO_ERROR,
            message = message ?: "I/O Error occurred",
            cause = this@handleRoomError
        )

        is IllegalStateException -> ErrorModel(
            type = CommonError.UNKNOWN,
            message = message ?: "IllegalStateException occurred",
            cause = this@handleRoomError
        )

        is SQLiteFullException -> ErrorModel(
            type = LocalError.DISK_FULL,
            message = message ?: "SqlLite Full Error",
            cause = this@handleRoomError
        )

        else -> ErrorModel(
            type = CommonError.UNKNOWN,
            message = message ?: "Unknown Error Occurred",
            cause = this@handleRoomError
        )
    }
    Log.e(ErrorModel.TAG, error.toString())
    return error
}