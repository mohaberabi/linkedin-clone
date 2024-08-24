package com.mohaberabi.linkedinclone.core.data.source.local.persistence

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.mohaberabi.linkedin.core.domain.source.local.persistence.PersistenceClient
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataStorePersistenceClient @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val dispatchers: DispatchersProvider,
) : PersistenceClient {
    override suspend fun write(
        key: String,
        value: String
    ) {
        withContext(dispatchers.io) {
            dataStore.safeCall {
                set(key, value)
            }
        }
    }

    override suspend fun delete(
        key: String,
    ) {
        withContext(dispatchers.io) {
            dataStore.safeCall {
                delete(key)
            }
        }
    }

    override suspend fun clear() {
        withContext(dispatchers.io) {
            dataStore.safeCall {
                edit { it.clear() }
            }
        }
    }


    override fun read(
        key: String,
    ): Flow<String?> = dataStore.read(key).flowOn(dispatchers.io)

}

