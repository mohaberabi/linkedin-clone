package com.mohaberabi.linkedin.core.domain.source.local

import com.mohaberabi.linkedin.core.domain.model.AppFile


interface FileCompressor {
    suspend fun compress(
        file: AppFile
    ): ByteArray
}


