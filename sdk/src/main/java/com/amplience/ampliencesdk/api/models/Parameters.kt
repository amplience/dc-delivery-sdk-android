package com.amplience.ampliencesdk.api.models

/**
 * @property depth - can be root or all
 * @property format - can be inlined or linked
 * @property locale
 */
data class Parameters(
    val depth: Depth = Depth.All,
    val format: Format = Format.Inlined,
    val locale: String? = null
)
