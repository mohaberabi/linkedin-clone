package com.mohaberabi.presentation.ui.util.extension

import android.content.ContentResolver
import android.net.Uri


fun Uri.asByteArray(
    resolver: ContentResolver,
): ByteArray? {
    return resolver.openInputStream(this).use { input ->
        input?.readBytes()
    }
}