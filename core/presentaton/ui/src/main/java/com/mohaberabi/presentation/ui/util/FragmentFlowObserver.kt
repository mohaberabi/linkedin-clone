package com.mohaberabi.presentation.ui.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


inline fun <reified T> Fragment.collectLifeCycleFlow(
    flow: Flow<T>,
    crossinline collector: (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect { t ->
                collector(t)
            }
        }
    }
}

inline fun <reified T> Fragment.eventCollector(
    flow: Flow<T>,
    crossinline collector: (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main.immediate) {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect { t ->
                collector(t)
            }
        }
    }
}