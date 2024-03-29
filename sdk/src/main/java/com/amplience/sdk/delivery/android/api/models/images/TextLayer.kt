package com.amplience.sdk.delivery.android.api.models.images

import java.net.URLEncoder

data class TextLayer(
    val text: String,
    val fontSize: Int? = null, // defaults to 10
    val fontFamily: String? = null, // If you don't specify a font family, or specify a font that is not installed on your account, then it will default to Helevetica.
    val fontStyle: FontStyle? = null,
    val fontWeight: Int? = null, // Valid values are from 100 to 900 in multiples of 100.
    val fontStretch: FontStretch? = null,
    val textColor: TextColor? = null,
    val textDecoration: Decoration? = null,
    val textAlign: String? = null
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

        addQuery("text", text)
        fontSize?.let { addQuery("fontSize", it) }
        fontFamily?.let { addQuery("fontFamily", it) }
        fontStyle?.let { addQuery("fontStyle", it) }
        fontWeight?.let { addQuery("fontWeight", it) }
        fontStretch?.let { addQuery("fontStretch", it) }
        textColor?.let { addQuery("textColor", it.toString(), preEncoded = true) }
        textDecoration?.let { addQuery("textDecoration", it) }
        textAlign?.let { addQuery("textAlign", it) }

        builder.append("]")
        return builder.toString()
    }

    enum class FontStyle {
        Normal, Italic, Oblique;

        override fun toString(): String = when (this) {
            Normal -> "normal"
            Italic -> "italic"
            Oblique -> "oblique"
        }
    }

    enum class FontStretch {
        UltraCondensed,
        ExtraCondensed,
        Condensed,
        SemiCondensed,
        Normal,
        SemiExpanded,
        Expanded,
        ExtraExpanded,
        UltraExpanded;

        override fun toString(): String = when (this) {
            UltraCondensed -> "ultra-condensed"
            ExtraCondensed -> "extra-condensed"
            Condensed -> "condensed"
            SemiCondensed -> "semi-condensed"
            Normal -> "normal"
            SemiExpanded -> "semi-expanded"
            Expanded -> "expanded"
            ExtraExpanded -> "extra-expanded"
            UltraExpanded -> "ultra-expanded"
        }
    }

    sealed class TextColor {
        data class Hex(val hex: String) : TextColor() {
            override fun toString(): String {
                val noHash = hex.removePrefix("#")
                val encoded = URLEncoder.encode(
                    noHash,
                    "UTF-8"
                )
                return "#$encoded"
            }
        }

        data class RGB(val red: Int, val green: Int, val blue: Int) : TextColor() {
            override fun toString(): String = "rgb($red,$green,$blue)"
        }

        data class ColorName(val name: String) : TextColor() {
            override fun toString(): String = URLEncoder.encode(name, "UTF-8")
        }
    }

    enum class Decoration {
        Underline,
        Overline,
        LineThrough;

        override fun toString(): String = when (this) {
            Underline -> "underline"
            Overline -> "overline"
            LineThrough -> "linethrough"
        }
    }
}
