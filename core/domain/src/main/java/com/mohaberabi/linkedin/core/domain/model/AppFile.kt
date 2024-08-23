package com.mohaberabi.linkedin.core.domain.model


enum class AppFileType {
    Image,
}

data class AppFile(
    val bytes: ByteArray,
    val type: AppFileType = AppFileType.Image,
    val compressedToExtension: AppFileType = AppFileType.Image,
    val reference: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AppFile

        return bytes.contentEquals(other.bytes)
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }
}