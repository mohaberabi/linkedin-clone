package com.mohaberabi.linkedinclone.core.remote_logging.domain.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach


interface RemoteLogging {
    fun recordException(
        e: Throwable? = null
    )

    fun log(info: LogInfo)
}


inline fun <reified T> Flow<T>.log(
    crossinline onDone: (LogInfo) -> Unit,
    crossinline map: (T) -> String,
): Flow<T> {
    return onEach {
        onDone(
            LogInfo.StateChanged(
                stateName = T::class.java.simpleName,
                value = map(it),
            )
        )
    }

}

inline fun <reified T> Flow<T>.logWithError(
    crossinline onDone: (LogInfo) -> Unit,
    crossinline map: (T) -> String,
    crossinline onError: (Throwable?) -> Unit,
): Flow<T> {
    return onEach {
        onDone(
            LogInfo.StateChanged(
                stateName = T::class.java.simpleName,
                value = map(it),
            )
        )
    }.catch {
        onError(it)
    }
}
