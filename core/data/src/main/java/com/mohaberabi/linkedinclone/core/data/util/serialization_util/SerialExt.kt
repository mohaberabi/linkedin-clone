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


//internal inline fun <reified T> T.encodeAsMap(): Map<String, Any> {
////    val json = Json.encodeToString(serializer(), this)
////    val map = Json.decodeFromString<Map<String, Any>>(json)
////    return map
//    val jsonElement: JsonElement = Json.encodeToJsonElement(serializer(), this)
//    return (jsonElement as JsonObject).mapValues { it.value.toString() }
//}
//
