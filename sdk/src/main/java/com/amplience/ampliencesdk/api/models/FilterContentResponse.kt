package com.amplience.ampliencesdk.api.models

data class FilterContentResponse(
    val responses: List<ListContentResponse>,
    val page: Page
) {
    data class Page(
        val responseCount: Int,
        val nextCursor: String?
    )
}
