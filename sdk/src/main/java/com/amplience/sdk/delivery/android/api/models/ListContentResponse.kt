package com.amplience.sdk.delivery.android.api.models

data class ListContentResponse(
    val content: Map<String, Any>?,
    val linkedContent: List<Map<String, Any>>?
)
