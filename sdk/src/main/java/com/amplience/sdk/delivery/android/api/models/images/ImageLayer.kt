package com.amplience.sdk.delivery.android.api.models.images

import java.net.URLEncoder

data class ImageLayer(
    val src: String,
    val width: Int? = null,
    val height: Int? = null,
    val topPx: Int? = null,
    val topPercent: Int? = null,
    val leftPx: Int? = null,
    val leftPercent: Int? = null,
    val bottomPx: Int? = null,
    val bottomPercent: Int? = null,
    val rightPx: Int? = null,
    val rightPercent: Int? = null,
    val anchor: Anchor? = null,
    val opacity: Int? = null
) : Layer {
    override fun toQuery(): String {
        val builder = StringBuilder("[")
        var firstQuery = true

        fun addQuery(queryName: String, vararg queries: Any, preEncoded: Boolean = false) {
            if (firstQuery) {
                firstQuery = false
            } else {
                builder.append("&")
            }
            builder.append("$queryName=")
            queries.forEachIndexed { index, query ->
                builder.append(
                    if (preEncoded) {
                        query.toString()
                    } else {
                        URLEncoder.encode(
                            query.toString(),
                            "UTF-8"
                        )
                    }
                )
                if (index != queries.indices.last()) {
                    builder.append(",")
                }
            }
        }

        addQuery("src", src, preEncoded = true)
        width?.let { addQuery("w", it) }
        height?.let { addQuery("h", it) }
        topPx?.let { addQuery("top", it) }
        topPercent?.let { addQuery("top", "$it%", preEncoded = true) }
        leftPx?.let { addQuery("left", it) }
        leftPercent?.let { addQuery("left", "$it%", preEncoded = true) }
        bottomPx?.let { addQuery("bottom", it) }
        bottomPercent?.let { addQuery("bottom", "$it%", preEncoded = true) }
        rightPx?.let { addQuery("right", it) }
        rightPercent?.let { addQuery("right", "$it%", preEncoded = true) }
        anchor?.let { addQuery("anchor", it) }
        opacity?.let { addQuery("opacity", it) }

        builder.append("]")
        return builder.toString()
    }
}
