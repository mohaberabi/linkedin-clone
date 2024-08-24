package com.mohaberabi.linkedin.core.domain.source.local.compressor

import com.mohaberabi.linkedin.core.domain.model.AppFileType


interface FileCompressorFactory {
    fun create(
        type: AppFileType,
    ): FileCompressor
}