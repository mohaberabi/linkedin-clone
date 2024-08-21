package com.mohaberabi.linkedin.core.domain.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatchersProvider {
    val io: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
}


