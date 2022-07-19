package com.amplience.ampliencesdk.api.models

/**
 * [FilterContentRequest]
 *
 * @property filterBy - [FilterBy]/s to match to
 * If you specify multiple filters then all filters must be matched (AND).
 * @property sortBy - Sort by key. Can optionally override order.
 * @property parameters - Override depth, format and locale
 * @property page - Pagination
 */
data class FilterContentRequest(
    val filterBy: List<FilterBy>,
    val sortBy: SortBy? = null,
    val page: Page? = null,
    val parameters: Parameters = Parameters()
) {

    /**
     * [Page] used in pagination requests
     *
     * @property size - The number of items to be returned in each request.
     * The maximum page size is 12. If size is not specified then the default
     * page size of 12 will be used.
     *
     * @property cursor (optional) - The starting point for the next request. Returned in
     * the nextCursor property of the response if there are further results.
     */
    data class Page(
        val size: Int,
        val cursor: String? = null
    )

    /**
     * @property depth - can be root or all
     * @property format - can be inlined or linked
     * @property locale
     */
    data class Parameters(
        val depth: ContentDepth = ContentDepth.All,
        val format: Format = Format.Inlined,
        val locale: String? = null
    )
}
