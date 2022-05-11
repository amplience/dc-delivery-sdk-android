package com.amplience.ampliencesdk.api.models

/**
 * @property depth - can be root or all
 * @property format - can be inlined or linked
 * @property locale
 */
data class Parameters(
    val depth: String = "all",
    val format: String = "inlined",
    val locale: String? = null
)
