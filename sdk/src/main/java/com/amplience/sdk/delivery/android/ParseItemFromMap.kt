@file:JvmName("ParseUtils")

package com.amplience.sdk.delivery.android

import com.google.gson.Gson

val gson = Gson()

/**
 * [parseToObject]
 * Turns a map into a specified data class, or returns null
 *
 * @receiver the map representing the JSON from the api
 * @return the data class specified, or null
 */
inline fun <reified T> Map<String, Any>.parseToObject(): T? {
    return try {
        val json = gson.toJsonTree(this)
        gson.fromJson(json, T::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * [parseToObject]
 * Turns a map into a specified data class, or returns null
 *
 * @param map representing the JSON from the api
 * @param classOfT class to parse the map into
 * @return the data class specified, or null
 */
fun <T> parseToObject(map: Map<String, Any>?, classOfT: Class<T>?): T? {
    return try {
        val json = gson.toJsonTree(map)
        gson.fromJson(json, classOfT)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * [parseToObject]
 * Turns a sub map into a specified data class, or returns null
 *
 * @param key - specify a key and this function will parse the value of that key into a data class
 *
 * @receiver the map representing the JSON from the api
 * @return the data class specified, or null
 */
inline fun <reified T> Map<String, Any>.parseToObject(key: String): T? {
    return try {
        val subMap = this[key] as Map<String, Any>
        val json = gson.toJsonTree(subMap)
        gson.fromJson(json, T::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * [parseToObject]
 * Turns a sub map into a specified data class, or returns null
 *
 * @param key - specify a key and this function will parse the value of that key into a data class
 * @param map the map representing the JSON from the api
 * @param classOfT class to parse the map into
 *
 * @return the data class specified, or null
 */
fun <T> parseToObject(key: String, map: Map<String, Any>?, classOfT: Class<T>?): T? {
    return try {
        val subMap = map?.get(key) as Map<String, Any>
        val json = gson.toJsonTree(subMap)
        gson.fromJson(json, classOfT)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * [parseToObjectList]
 * Turns a list of maps into a list of a specified data class, or returns null
 *
 * @receiver a list of maps representing the JSON from the api
 * @return a list of the data class specified, or null
 */
inline fun <reified T> List<Map<String, Any>>.parseToObjectList(): List<T>? {
    return try {
        map { subMap ->
            val json = gson.toJsonTree(subMap)
            gson.fromJson(json, T::class.java)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * [parseToObjectList]
 * Turns a list of maps into a list of a specified data class, or returns null
 *
 * @param map a list of maps representing the JSON from the api
 * @param classOfT class to parse the map into
 *
 * @return a list of the data class specified, or null
 */
fun <T> parseToObjectList(map: Map<String, Any>?, classOfT: Class<T>?): List<T>? {
    return try {
        map?.map { subMap ->
            val json = gson.toJsonTree(subMap)
            gson.fromJson(json, classOfT)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * [parseToObjectList]
 * Turns a list of maps into a list of a specified data class, or returns null
 *
 * @param key - specify a key and this function will parse the value of that key into a data class
 *
 * @receiver a list of maps representing the JSON from the api
 * @return a list of the data class specified, or null
 */
inline fun <reified T> Map<String, Any>.parseToObjectList(key: String): List<T>? {
    return try {
        val mapList = this[key] as List<Map<String, Any>>
        mapList.map { subMap ->
            val json = gson.toJsonTree(subMap)
            gson.fromJson(json, T::class.java)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * [parseToObjectList]
 * Turns a list of maps into a list of a specified data class, or returns null
 *
 * @param key - specify a key and this function will parse the value of that key into a data class
 * @param map a list of maps representing the JSON from the api
 * @param classOfT class to parse the map into
 *
 * @return a list of the data class specified, or null
 */
fun <T> parseToObjectList(key: String, map: Map<String, Any>?, classOfT: Class<T>?): List<T>? {
    return try {
        val mapList = map?.get(key) as List<Map<String, Any>>
        mapList.map { subMap ->
            val json = gson.toJsonTree(subMap)
            gson.fromJson(json, classOfT)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
