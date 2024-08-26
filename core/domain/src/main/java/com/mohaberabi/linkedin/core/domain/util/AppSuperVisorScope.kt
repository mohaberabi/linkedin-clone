package com.mohaberabi.linkedin.core.domain.util

import kotlinx.coroutines.CoroutineScope


interface AppSuperVisorScope {
    operator fun invoke(): CoroutineScope
}