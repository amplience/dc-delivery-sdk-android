package com.amplience.ampliencesdk.api.models

/**
 * [FilterBy] properties must be specified in the trait:filterable section of a content type schema
 *
 * @property path - The JSON pointer format path to the filterable property to use as a filter.
 * @property value - The value to match against
 */
data class FilterBy(
    val path: String,
    val value: String
)
