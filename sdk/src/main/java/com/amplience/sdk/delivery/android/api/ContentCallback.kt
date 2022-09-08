package com.amplience.sdk.delivery.android.api

interface ContentCallback<T> {
    fun onSuccess(result: T)
    fun onError(exception: Exception)
}
