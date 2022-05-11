package com.amplience.ampliencesdk.api.models

data class ContentRequest(
    val requests: List<Request>,
    val parameters: Parameters = Parameters()
) {
    data class Request(
        val id: String? = null,
        val key: String? = null
    )
}
