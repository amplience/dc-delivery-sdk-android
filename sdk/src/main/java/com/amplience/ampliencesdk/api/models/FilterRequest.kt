package com.amplience.ampliencesdk.api.models

/**
 * [FilterRequest]
 *
 * @property filterBy - [FilterBy]/s to match to
 * If you specify multiple filters then all filters must be matched (AND).
 * @property sortBy - Sort by key. Can optionally override order.
 * @property parameters - Override depth, format and locale
 * @property page - Pagination
 */
data class FilterRequest(
    val filterBy: List<FilterBy>,
    val sortBy: SortBy? = null,
    val parameters: Parameters? = null,
    val page: Page? = null
)
