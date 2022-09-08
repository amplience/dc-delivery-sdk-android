package com.amplience.sdk.delivery.android.api.models

data class FilterContentResponse(
    val responses: List<ListContentResponse>,
    val page: Page
) {
    data class Page(
        val responseCount: Int,
        val nextCursor: String?
    )
}
