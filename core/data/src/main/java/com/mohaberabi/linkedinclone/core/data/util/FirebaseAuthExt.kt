package com.mohaberabi.linkedinclone.core.data.util


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.mohaberabi.linkedin.core.domain.error.AppException
import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.error.RemoteError
import kotlin.coroutines.cancellation.CancellationException

suspend fun <T> FirebaseAuth.safeCall(
    operation: suspend FirebaseAuth.() -> T
): T {
    return try {
        this.operation()
    } catch (e: FirebaseAuthException) {
        throw AppException.RemoteException(e.toErrorModel())
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        throw AppException.RemoteException(
            ErrorModel(
                type = RemoteError.UNKNOWN_ERROR,
                message = e.message ?: "Unknown Error Happened ",
                cause = e
            )
        )
    }
}
