package com.amplience.ampliencesdk

import com.google.gson.Gson

inline fun <reified T> Map<String, Any>.parseToObject(key: String): T? {
    return try {
        val subMap = this[key] as Map<String, Any>
        val gson = Gson()
        val json = gson.toJsonTree(subMap)
        val o = gson.fromJson(json, T::class.java)
        o
    } catch (e: Exception) {
        null
    }
}

inline fun <reified T> Map<String, Any>.parseToObjectList(key: String): List<T>? {
    return try {
        val subMap = this[key] as Map<String, Any>
        val gson = Gson()
        val json = gson.toJsonTree(subMap)
        val o = gson.fromJson(json, Array<T>::class.java)
        o.toList()
    } catch (e: Exception) {
        null
    }
}
