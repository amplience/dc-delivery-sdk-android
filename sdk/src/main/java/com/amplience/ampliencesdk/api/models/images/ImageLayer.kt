package com.amplience.ampliencesdk.api.models.images

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
): Layer {
    override fun toQuery(): String {
        val builder = StringBuilder("[")
        var firstQuery = true

        fun addQuery(query: String) {
            if (firstQuery) {
                builder.append("?")
                firstQuery = false
            } else {
                builder.append("&")
            }
            builder.append(query)
        }

        addQuery("src=$src")
        width?.let { addQuery("w=$it") }
        height?.let { addQuery("h=$it") }
        topPx?.let { addQuery("top=$it") }
        topPercent?.let { addQuery("top=$it") }
        leftPx?.let { addQuery("left=$it") }
        leftPercent?.let { addQuery("left=$it") }
        bottomPx?.let { addQuery("bottom=$it") }
        bottomPercent?.let { addQuery("bottom=$it") }
        rightPx?.let { addQuery("right=$it") }
        rightPercent?.let { addQuery("right=$it") }
        anchor?.let { addQuery("anchor=$it") }
        opacity?.let { addQuery("opacity=$it") }

        builder.append("]")
        return builder.toString()
    }
}
