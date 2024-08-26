package com.mohaberabi.presentation.ui.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

inline fun <reified T> LifecycleOwner.collectLifeCycleFlow(
    flow: Flow<T>,
    crossinline collector: (T) -> Unit
) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect { t ->
                collector(t)
            }
        }
    }
}

