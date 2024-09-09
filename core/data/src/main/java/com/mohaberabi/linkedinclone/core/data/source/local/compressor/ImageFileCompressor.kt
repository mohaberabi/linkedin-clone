package com.mohaberabi.linkedinclone.core.data.source.local.compressor

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.mohaberabi.linkedin.core.domain.model.AppFile
import com.mohaberabi.linkedin.core.domain.model.AppFileType
import com.mohaberabi.linkedin.core.domain.source.local.compressor.FileCompressor
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject


class ImageFileCompressor @Inject constructor(
    private val dispatchers: DispatchersProvider
) : FileCompressor {
    override suspend fun compress(
        file: AppFile,
    ): ByteArray {
        return withContext(dispatchers.default) {
            val bitmap = BitmapFactory.decodeByteArray(
                file.bytes,
                0,
                file.bytes.size
            )

            val output = ByteArrayOutputStream()
            val outputFormat = when (file.compressedToExtension) {
                AppFileType.Image -> Bitmap.CompressFormat.JPEG
            }
            bitmap.compress(
                outputFormat,
                100,
                output
            )
            output.toByteArray()
        }
    }
}