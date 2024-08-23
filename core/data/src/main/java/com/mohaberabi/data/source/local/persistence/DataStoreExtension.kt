package com.mohaberabi.data.source.local.persistence


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mohaberabi.linkedin.core.domain.error.AppException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

suspend fun <T> DataStore<Preferences>.safeCall(
    operation: suspend DataStore<Preferences>.() -> T
): T {
    return try {
        operation()
    } catch (e: Exception) {
        throw AppException.LocalException(e.handleDataStore())
    }
}

internal fun DataStore<Preferences>.read(
    key: String,
): Flow<String?> {
    return data.map {
        it[stringPreferencesKey(key)]
    }
}

internal suspend fun DataStore<Preferences>.delete(
    key: String,
) {
    edit { prefs ->
        prefs.remove(stringPreferencesKey(key))
    }
}

internal suspend fun DataStore<Preferences>.set(
    key: String,
    value: String,
) {
    edit { prefs ->
        prefs[stringPreferencesKey(key)] = value
    }
}