package com.amplience.ampliencesdk.api.models

data class FilterContentResponse(
    val responses: List<ListContentResponse>,
    val page: PageData
)
