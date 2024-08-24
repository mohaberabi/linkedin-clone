package com.mohaberabi.linkedinclone.core.data.util


import android.util.Log
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.error.RemoteError

fun FirebaseAuthException.toErrorModel(): ErrorModel {
    val remoteError = when (this) {
        is FirebaseAuthInvalidCredentialsException -> RemoteError.INVALID_CREDENTIALS
        is FirebaseAuthInvalidUserException -> RemoteError.INVALID_USER
        else -> when (this.errorCode) {
            FirebaseErrorCodes.WRONG_PASSWORD -> RemoteError.INVALID_CREDENTIALS
            FirebaseErrorCodes.USER_NOT_FOUND -> RemoteError.INVALID_USER
            FirebaseErrorCodes.EMAIL_ALREADY_IN_USE -> RemoteError.CONFLICT
            FirebaseErrorCodes.INVALID_EMAIL -> RemoteError.WRONG_EMAIL_USERNAME
            FirebaseErrorCodes.TOO_MANY_REQUESTS -> RemoteError.TOO_MANY_REQUEST
            FirebaseErrorCodes.USER_DISABLED -> RemoteError.UNAUTHORIZED
            else -> RemoteError.UNKNOWN_ERROR
        }
    }
    val error = ErrorModel(
        message = this@toErrorModel.message,
        cause = this@toErrorModel,
        type = remoteError
    )
    Log.e("appError", error.toString())
    return error
}
