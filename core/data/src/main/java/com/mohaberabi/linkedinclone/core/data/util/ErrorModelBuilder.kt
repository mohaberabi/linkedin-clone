package com.mohaberabi.linkedinclone.core.data.util

import android.util.Log
import com.mohaberabi.linkedin.core.domain.error.AppError
import com.mohaberabi.linkedin.core.domain.error.ErrorModel

class ErrorModelBuilder(
    private val type: AppError,
) {
    var message: String? = null
    var statusCode: Int? = null
    var cause: Exception? = null

    fun build(): ErrorModel {
        return ErrorModel(
            message = message,
            statusCode = statusCode,
            cause = cause,
            type = type
        )
    }
}

fun errorModel(
    type: AppError,
    action: ErrorModelBuilder.() -> Unit = {},
): ErrorModel {
    val errorModel = ErrorModelBuilder(type).apply(action).build()

    Log.e("appError", errorModel.toString())
    return errorModel
}
