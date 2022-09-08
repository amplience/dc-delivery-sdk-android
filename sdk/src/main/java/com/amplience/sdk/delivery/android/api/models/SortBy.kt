package com.amplience.sdk.delivery.android.api.models

/**
 * @property key - The sort key defined in the schema. The sort key must be specified in the
 * trait:sortable section of each schema that contains the filterable property.
 * @property order - Optional, can be [Order.ASC] (ascending) or [Order.DESC] descending. ASC by default
 */
data class SortBy(
    val key: String,
    val order: Order? = null
) {
    enum class Order {
        ASC, DESC;
    }
}
