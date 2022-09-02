package com.amplience.ampliencesdk.api

interface ContentCallback<T> {
    fun onSuccess(result: T)
    fun onError(exception: Exception)
}
