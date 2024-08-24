package com.mohaberabi.linkedinclone.core.data.source.local.compressor

import com.mohaberabi.linkedin.core.domain.model.AppFileType
import com.mohaberabi.linkedin.core.domain.source.local.compressor.FileCompressor
import com.mohaberabi.linkedin.core.domain.source.local.compressor.FileCompressorFactory
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import javax.inject.Inject


class DefaultFileCompressorFactory @Inject constructor(
    private val dispatchers: DispatchersProvider
) : FileCompressorFactory {
    override fun create(
        type: AppFileType,
    ): FileCompressor {
        return when (type) {
            AppFileType.Image -> ImageFileCompressor(
                dispatchers = dispatchers,
            )
        }
    }

}