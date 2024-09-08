package com.mohaberabi.linkedin.core.domain.source.remote

interface StorageClient {
    suspend fun upload(
        bytes: ByteArray,
        reference: String,
    ): String
}