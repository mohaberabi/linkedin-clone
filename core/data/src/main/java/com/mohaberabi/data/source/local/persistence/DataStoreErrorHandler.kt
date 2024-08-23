package com.mohaberabi.data.source.local.persistence

import androidx.datastore.core.IOException
import com.mohaberabi.data.util.errorModel
import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.error.LocalError


internal fun Exception.handleDataStore(): ErrorModel {
    return when (this) {
        is IOException -> errorModel(LocalError.IO) {
            message = "I/O Error occurred"
            cause = this@handleDataStore
        }

        is IllegalStateException -> errorModel(LocalError.ILLEGAL_STATE) {
            message = "Illegal state detected"
            cause = this@handleDataStore
        }

        else -> errorModel(LocalError.UNKNOWN) {
            message = this@handleDataStore.message ?: "Unknown error"
            cause = this@handleDataStore
        }
    }
}