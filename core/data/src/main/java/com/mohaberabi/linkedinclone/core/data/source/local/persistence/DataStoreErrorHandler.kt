package com.mohaberabi.linkedinclone.core.data.source.local.persistence

import android.util.Log
import androidx.datastore.core.IOException
import com.mohaberabi.linkedin.core.domain.error.CommonError
import com.mohaberabi.linkedin.core.domain.error.ErrorModel


internal fun Exception.handleDataStore(): ErrorModel {
    val error = when (this) {
        is IOException -> ErrorModel(
            type = CommonError.IO_ERROR,
            message = "I/O Error occurred",
            cause = this@handleDataStore
        )

        is IllegalStateException -> ErrorModel(
            type = CommonError.ILLEGAL_STATE,
            message = "Illegal state detected",
            cause = this@handleDataStore
        )

        else -> ErrorModel(
            type = CommonError.UNKNOWN,
            message = this@handleDataStore.message ?: "Unknown error",
            cause = this@handleDataStore
        )
    }
    Log.e(ErrorModel.TAG, error.toString())
    return error
}