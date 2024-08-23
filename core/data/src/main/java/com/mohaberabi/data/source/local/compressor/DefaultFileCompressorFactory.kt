package com.mohaberabi.data.source.local.compressor

import com.mohaberabi.linkedin.core.domain.model.AppFileType
import com.mohaberabi.linkedin.core.domain.source.local.FileCompressor
import com.mohaberabi.linkedin.core.domain.source.local.FileCompressorFactory
import javax.inject.Inject


class DefaultFileCompressorFactory @Inject constructor(
    private val imageFileCompressor: ImageFileCompressor,
) : FileCompressorFactory {
    override fun create(
        type: AppFileType,
    ): FileCompressor {
        return when (type) {
            AppFileType.Image -> imageFileCompressor
        }
    }

}