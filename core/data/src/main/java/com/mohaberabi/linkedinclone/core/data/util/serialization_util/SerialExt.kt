package com.mohaberabi.linkedinclone.core.data.util.serialization_util


import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer


internal inline fun <reified T> String.decodeAsList(): List<T> {
    return Json.decodeFromString<List<T>>(serializer(), this)
}

internal inline fun <reified T> String?.decode(): T? {
    return this?.let {
        Json.decodeFromString<T>(serializer(), it)
    }
}

internal inline fun <reified T> T.encode(): String {
    return Json.encodeToString(serializer(), this)
}

internal inline fun <reified T> List<T>.encode(): String {
    return Json.encodeToString(serializer(), this)
}
