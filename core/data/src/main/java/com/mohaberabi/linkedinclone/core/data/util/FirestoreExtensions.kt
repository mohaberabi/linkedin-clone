package com.mohaberabi.linkedinclone.core.data.util

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import com.mohaberabi.linkedin.core.domain.error.AppException
import com.mohaberabi.linkedin.core.domain.error.RemoteError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await


suspend inline fun <reified T> FirebaseFirestore.paginate(
    limit: Long = 20L,
    lastDocId: String? = null,
    collection: CollectionReference,
    orderBy: String,
    ascending: Boolean = false,
): List<T> {
    return paginateInternal(
        limit = limit,
        lastDocId = lastDocId,
        query = collection,
        orderBy = orderBy,
        ascending = ascending
    )
}

suspend inline fun <reified T> FirebaseFirestore.paginate(
    limit: Long = 20L,
    lastDocId: String? = null,
    coll: String,
    orderBy: String,
    ascending: Boolean = false,
): List<T> {
    val collection = this.collection(coll)
    return paginateInternal(
        limit = limit,
        lastDocId = lastDocId,
        query = collection,
        orderBy = orderBy,
        ascending = ascending
    )
}


inline fun <reified T> FirebaseFirestore.listenAndPaginate(
    limit: Long,
    lastDocId: String?,
    query: CollectionReference,
    orderBy: String,
    ascending: Boolean,
): Flow<List<T>> {
    return flow {
        val querySnapshot = paginatedQuery(
            limit = limit,
            lastDocId = lastDocId,
            query = query,
            orderBy = orderBy,
            ascending = ascending,
        )
        querySnapshot
            .snapshots()
            .map { snapshot ->
                val list = snapshot.documents
                    .map {
                        it.toObject(T::class.java)
                    }
                list
            }
    }
}

suspend inline fun <reified T> FirebaseFirestore.paginateInternal(
    limit: Long,
    lastDocId: String?,
    query: CollectionReference,
    orderBy: String,
    ascending: Boolean,
): List<T> {
    val paginatedQuery =
        paginatedQuery(
            limit = limit,
            lastDocId = lastDocId,
            query = query,
            orderBy = orderBy,
            ascending = ascending
        )
    return paginatedQuery.limit(limit).get().await().mapNotNull { it.toObject(T::class.java) }
}

suspend fun <T> FirebaseFirestore.safeCall(
    operation: suspend FirebaseFirestore.() -> T
): T {
    return try {
        this.operation()
    } catch (e: FirebaseFirestoreException) {
        throw AppException.RemoteException(e.toErrorModel())
    } catch (e: Exception) {
        e.printStackTrace()
        throw AppException.RemoteException(
            errorModel(RemoteError.UNKNOWN_ERROR) {
                message = e.message
                cause = e
            }
        )
    }
}

suspend fun paginatedQuery(
    limit: Long,
    lastDocId: String?,
    query: CollectionReference,
    orderBy: String,
    ascending: Boolean,
): Query {
    val queryDirection = if (ascending) {
        com.google.firebase.firestore.Query.Direction.ASCENDING
    } else {
        com.google.firebase.firestore.Query.Direction.DESCENDING
    }
    val baseQuery = query.orderBy(orderBy, queryDirection)
    val document = lastDocId?.let {
        query.document(it).get().await()
    }
    val paginatedQuery = document?.let { baseQuery.startAfter(it) } ?: baseQuery
    return paginatedQuery.limit(limit)
}