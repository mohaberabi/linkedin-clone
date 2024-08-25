package com.mohaberabi.linkedinclone.core.data.util


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.mohaberabi.linkedin.core.domain.error.AppException
import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.error.RemoteError

suspend fun <T> FirebaseAuth.safeCall(
    operation: suspend FirebaseAuth.() -> T
): T {
    return try {
        this.operation()
    } catch (e: FirebaseAuthException) {
        throw AppException.RemoteException(e.toErrorModel())
    } catch (e: Exception) {
        throw AppException.RemoteException(
            errorModel(RemoteError.UNKNOWN_ERROR) {
                message = e.message
                cause = e
            }
        )
    }
}
