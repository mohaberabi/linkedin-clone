package com.mohaberabi.linkedinclone.core.data.util

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.mohaberabi.linkedin.core.domain.error.AppException
import com.mohaberabi.linkedin.core.domain.error.RemoteError
import kotlinx.coroutines.tasks.await


suspend inline fun <reified T> FirebaseFirestore.paginate(
    limit: Long = 20L,
    lastDocId: String? = null,
    collection: CollectionReference,
    orderBy: String,
    ascending: Boolean = false,
    filters: List<Filter> = listOf()
): List<T> {
    return paginatedQuery(
        limit = limit,
        filters = filters,
        lastDocId = lastDocId,
        query = collection,
        orderBy = orderBy,
        ascending = ascending
    ).get().await().map { it.toObject(T::class.java) }
}

suspend inline fun <reified T> FirebaseFirestore.paginate(
    limit: Long = 20L,
    lastDocId: String? = null,
    coll: String,
    orderBy: String,
    ascending: Boolean = false,
    filters: List<Filter> = listOf()
): List<T> {
    val collection = this.collection(coll)
    return paginatedQuery(
        limit = limit,
        filters = filters,
        lastDocId = lastDocId,
        query = collection,
        orderBy = orderBy,
        ascending = ascending
    ).get().await().map { it.toObject(T::class.java) }
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
    filters: List<Filter> = listOf()
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
    return applyFilters(
        query = paginatedQuery.limit(limit),
        filters = filters
    )
}

fun applyFilters(
    query: Query,
    filters: List<Filter> = listOf()
): Query {
    var filteredQuery = query
    filters.forEach { filter ->
        filteredQuery = filteredQuery.where(filter)
    }

    return filteredQuery
}