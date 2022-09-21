package com.amplience.sdk.delivery.android.api.models.images

import java.net.URLEncoder

class ImageUrlBuilder {
    private var width: Int? = null
    private var height: Int? = null
    private var maxWidth: Int? = null
    private var maxHeight: Int? = null
    private var scaleMode: ScaleMode? = null
    private var scaleFit: ScaleFit? = null
    private var resizeAlgorithm: ResizeAlgorithm? = null
    private var upscale: Upscale? = null
    private var format: ContentFormat? = null
    private var formatQuality: FormatQuality? = null

    /**
     * [ImageUrlBuilder.width]
     * Sets the width of the image. If you only provide the width, the height will be calculated to maintain the aspect ratio.
     *
     * @param width in pixels
     */
    fun width(width: Int) = apply { this.width = width }

    /**
     * [ImageUrlBuilder.height]
     * Sets the height of the image. If you only provide the height, the width will be calculated to maintain the aspect ratio.
     *
     * @param height in pixels
     */
    fun height(height: Int) = apply { this.height = height }

    /**
     * [ImageUrlBuilder.maxWidth]
     * Sets the maximum width for the image returned. Can be specified at the account level
     *
     * @param maxWidth in pixels
     */
    fun maxWidth(maxWidth: Int) = apply { this.maxWidth = maxWidth }

    /**
     * [ImageUrlBuilder.maxHeight]
     * Sets the maximum height for the image returned. Can be specified at the account level
     *
     * @param maxHeight in pixels
     */
    fun maxHeight(maxHeight: Int) = apply { this.maxHeight = maxHeight }

    /**
     * [ImageUrlBuilder.scaleMode]
     * Indicates how to position the image if it does not fit exactly to the width and height specified.
     * @see [ScaleMode] for options
     *
     * @param scaleMode
     */
    fun scaleMode(scaleMode: ScaleMode) = apply {
        this.scaleMode = scaleMode
        if (scaleMode is ScaleMode.Clamp) {
            width = scaleMode.width
            height = scaleMode.height
        }
    }

    /**
     * [ImageUrlBuilder.resize]
     * The algorithm to use when the image is resized.
     * @see [ResizeAlgorithm] for more details
     *
     * @param resizeAlgorithm
     */
    fun resize(resizeAlgorithm: ResizeAlgorithm) = apply { this.resizeAlgorithm = resizeAlgorithm }

    /**
     * [ImageUrlBuilder.upscale]
     * [Upscale.True] / [Upscale.False] / [Upscale.Padd]
     * Indicates if the system can scale the image to be bigger than the original input.
     * If set to [Upscale.Padd] the image will be drawn on top of a canvas with the size given.
     * The alignment of the image is set using [scaleMode].
     * The background colour of the canvas is set using [backgroundRgb].
     * @see [Upscale] for more details
     *
     * @param upscale
     */
    fun upscale(upscale: Upscale) = apply { this.upscale = upscale }

    /**
     * [ImageUrlBuilder.format]
     * Sets the format of the image, if this is not specified the format will be the same as the original.
     * Supported formats include:
     *   - [ContentFormat.Jpeg]
     *   - [ContentFormat.Png]
     *   - [ContentFormat.Gif]
     *   - [ContentFormat.Bmp]
     *   - [ContentFormat.Webp]
     *   - [ContentFormat.Jp2]
     * Note that webp is not supported on certain browsers. jp2 is currently only supported in Safari for iOS and macOS.
     *
     * @param format - choose from 6 [ContentFormat]s
     * @param quality - select a quality percentage 0-100 (does not apply to [ContentFormat.Gif] or [ContentFormat.Bmp])
     */
    @JvmOverloads
    fun format(format: ContentFormat, quality: Int? = null) = apply {
        this.format = format
        formatQuality = when (format) {
            ContentFormat.Webp -> quality?.let { FormatQuality.Webp(it) }
            ContentFormat.Jp2 -> quality?.let { FormatQuality.Jp2(it) }
            ContentFormat.Jpeg -> quality?.let { FormatQuality.Jpeg(it) }
            ContentFormat.Png -> quality?.let { FormatQuality.Png(it) }
            ContentFormat.Gif, ContentFormat.Bmp -> null
        }
    }

    internal fun build(): String {
        val builder = StringBuilder()
        var firstQuery = true

        fun addQuery(queryName: String, vararg queries: Any, preEncoded: Boolean = false) {
            if (firstQuery) {
                builder.append("?")
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

        if (width != null) addQuery("w", width!!)
        if (height != null) addQuery("h", height!!)
        if (maxWidth != null) addQuery("maxW", maxWidth!!)
        if (maxHeight != null) addQuery("maxH", maxHeight!!)
        if (scaleMode != null) {
            addQuery("sm", scaleMode!!)
            if (scaleMode is ScaleMode.AspectRatio) {
                val ratio = (scaleMode as ScaleMode.AspectRatio).ratio
                val ratioParts = ratio.split(":")
                val encodedRatio = ratioParts.joinToString(separator = ":") {
                    URLEncoder.encode(it, "UTF-8")
                }
                addQuery("aspect", encodedRatio, preEncoded = true)
            }
            if (scaleMode is ScaleMode.Edge) {
                val type = (scaleMode as ScaleMode.Edge).type
                val length = (scaleMode as ScaleMode.Edge).length
                addQuery("resize.edge", type)
                addQuery("resize.edge.length", length)
            }
        }
        if (scaleFit != null) addQuery("scalefit", scaleFit!!)
        if (resizeAlgorithm != null) addQuery("filter", resizeAlgorithm!!)
        if (upscale != null) addQuery("upscale", upscale!!)
        if (format != null) addQuery("fmt", format!!)
        if (formatQuality != null) addQuery(
            formatQuality!!.queryString,
            formatQuality!!.quality
        )

        return builder.toString()
    }
}
