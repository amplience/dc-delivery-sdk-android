package com.amplience.ampliencesdk.api.models.images

/**
 * Takes 4 parameters: [left], [top], [right], [bottom] to crop from each edge of the image
 */
data class EdgeCrop(
    val left: Int,
    val top: Int,
    val right: Int,
    val bottom: Int
)
