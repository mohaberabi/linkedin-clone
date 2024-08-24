package com.mohaberabi.linkedin.core.domain.source.local.persistence

import kotlinx.coroutines.flow.Flow


interface PersistenceClient {

    suspend fun write(key: String, value: String)
    suspend fun delete(key: String)
    suspend fun clear()
    fun read(key: String): Flow<String?>
}