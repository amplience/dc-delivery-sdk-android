package com.amplience.ampliencesdk.api.models

data class PagedResponse(
    val responses: List<Map<String, Any>>,
    val page: PageData
)
