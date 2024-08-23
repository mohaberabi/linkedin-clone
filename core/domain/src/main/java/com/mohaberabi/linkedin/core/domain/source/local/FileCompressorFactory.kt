package com.mohaberabi.linkedin.core.domain.source.local

import com.mohaberabi.linkedin.core.domain.model.AppFileType


interface FileCompressorFactory {
    fun create(type: AppFileType): FileCompressor
}