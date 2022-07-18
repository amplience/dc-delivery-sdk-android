package com.amplience.ampliencesdk.api.models

data class ListContentRequest(
    val requests: List<Request>,
    val parameters: Parameters = Parameters()
)
