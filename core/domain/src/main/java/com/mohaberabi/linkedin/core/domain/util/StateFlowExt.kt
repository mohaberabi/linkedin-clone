package com.mohaberabi.linkedin.core.domain.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


fun <T> MutableStateFlow<T>.logIn(
    scope: CoroutineScope,
    action: suspend (T) -> Unit
): MutableStateFlow<T> {
    scope.launch {

        onEach { action(it) }
    }
    return this
}