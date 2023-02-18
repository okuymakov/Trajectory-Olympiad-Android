package com.example.trajectoryolympiad.base

import android.os.Bundle
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


inline fun <reified T> Bundle.getObject(key: String?): T? {
    val string = getString(key) ?: return null
    return Json.decodeFromString(string)
}

inline fun <reified T> Bundle.putObject(key: String?, value: T) {
    val string = Json.encodeToString(value)
    putString(key, string)
}