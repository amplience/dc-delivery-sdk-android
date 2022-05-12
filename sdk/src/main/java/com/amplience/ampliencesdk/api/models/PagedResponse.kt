package com.amplience.ampliencesdk.api.models

data class PagedResponse(
    val responses: List<ContentResponse>,
    val page: PageData
)
