package com.mohaberabi.linkedinclone.firestore


import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirebaseFirestoreException.Code.*
import com.mohaberabi.data.util.errorModel
import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.error.RemoteError

fun FirebaseFirestoreException.toErrorModel(): ErrorModel {
    val remoteError = when (this.code) {
        PERMISSION_DENIED, UNAUTHENTICATED -> RemoteError.UNAUTHORIZED
        DEADLINE_EXCEEDED -> RemoteError.REQUEST_TIMEOUT
        ABORTED -> RemoteError.CONFLICT
        RESOURCE_EXHAUSTED -> RemoteError.TOO_MANY_REQUEST
        INVALID_ARGUMENT -> RemoteError.PAYLOAD_TOO_LARGE
        UNAVAILABLE -> RemoteError.NO_NETWORK
        INTERNAL -> RemoteError.SERVER_ERROR
        UNKNOWN -> RemoteError.UNKNOWN_ERROR
        else -> RemoteError.UNKNOWN_ERROR
    }
    return errorModel(remoteError) {
        message = this@toErrorModel.message
        cause = this@toErrorModel
    }

}
