package com.amplience.ampliencesdk.api.models

data class ContentRequest(
    val requests: List<Request>,
    val parameters: Parameters = Parameters()
)
