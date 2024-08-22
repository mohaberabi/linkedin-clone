package com.mohaberabi.linkedinclone.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObject
import com.mohaberabi.data.util.errorModel
import com.mohaberabi.linkedin.core.domain.error.AppException
import com.mohaberabi.linkedin.core.domain.error.RemoteError
import kotlinx.coroutines.tasks.await


suspend inline fun <reified T> FirebaseFirestore.paginate(
    limit: Long = 20L,
    lastDocId: String? = null,
    coll: String,
    orderBy: String,
): List<T> {
    val query = collection(coll).orderBy(orderBy)
    val document = lastDocId?.let {
        collection(coll).document(it).get().await()
    }
    val paginated = document?.let { query.startAfter(it) } ?: query
    return paginated.limit(limit).get().await().mapNotNull { it.toObject(T::class.java) }
}

suspend fun <T> FirebaseFirestore.safeCall(
    operation: suspend FirebaseFirestore.() -> T
): T {
    return try {
        this.operation()
    } catch (e: FirebaseFirestoreException) {
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
