package com.amplience.sdk.delivery.android.api.models.images

import java.net.URLEncoder

class ImageUrlBuilder {
    private var width: Int? = null
    private var height: Int? = null
    private var maxWidth: Int? = null
    private var maxHeight: Int? = null
    private var quality: Int? = null
    private var defaultQuality: Boolean? = null
    private var scaleMode: ScaleMode? = null
    private var scaleFit: ScaleFit? = null
    private var resizeAlgorithm: ResizeAlgorithm? = null
    private var upscale: Upscale? = null
    private var format: ContentFormat? = null
    private var formatQuality: FormatQuality? = null
    private var crop: Crop? = null
    private var edgeCrop: EdgeCrop? = null
    private var preCrop: Crop? = null
    private var preEdgeCrop: EdgeCrop? = null
    private var rotateDegrees: Int? = null
    private var rgb: Triple<Int, Int, Int>? = null
    private var preRotate: Boolean? = null
    private var flipH: Boolean = false
    private var flipV: Boolean = false
    private var dpi: Int? = null
    private var dpiFilter: DpiFilter? = null
    private var strip: Boolean = false
    private var chromaSubsampling: Boolean = true
    private var colorSpace: ColorSpace? = null
    private var unsharp: Unsharp? = null
    private var compositeMode: CompositeMode? = null
    private var backgroundRgb: Triple<Int, Int, Int>? = null
    private var indexed: Boolean = false
    private var paletteSize: Int? = null
    private var dithered: Boolean = true
    private var blur: Blur? = null
    private var reduceNoise: Int? = null
    private var gamma: Float? = null
    private var hue: Int? = null
    private var saturation: Int? = null
    private var brightness: Int? = null

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
     * [ImageUrlBuilder.quality]
     * Sets the compression quality of the image, this is a percentage 0-100. JPEGs are lossy, PNGs are lossless but are compressed with zlib.
     *
     *  @param quality percent 0 - 100
     */
    fun quality(quality: Int) = apply { this.quality = quality }

    /**
     * [ImageUrlBuilder.defaultQuality]
     * Specifies that the default quality should be used for the following formats: webp,jp2,jpeg or png. Can be used with fmt=auto or on its own. See auto format for more details.
     * The default settings for each format are:
     *     - jp2 40
     *     - webp 80
     *     - jpeg 75
     *     - png 90
     *
     * You can add your own quality settings in the root template for your account or transformation templates.
     */
    fun defaultQuality() = apply {
        this.defaultQuality = true
        quality = null
    }

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
     * [ImageUrlBuilder.scaleFit]
     * Specifies how to position the area to be cropped when using sm.
     *     - center (default)
     *     - poi - Crop using the specified point of interest focal point
     * @see [ScaleFit] for more detail
     *
     * @param scaleFit
     */
    fun scaleFit(scaleFit: ScaleFit) = apply { this.scaleFit = scaleFit }

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

    /**
     * [ImageUrlBuilder.crop]
     * Cuts out a section of the image, the coordinates are specified as x, y, w, h.
     * This command is applied after any resize so the coordinates are relative to the resized image.
     *
     * @param x - the offset from the top left of the image
     * @param y - the offset from the top of the image
     * @param w - the width of the selection
     * @param h - the height of the selection
     */
    fun crop(
        x: Int, // offset from the top left of the image
        y: Int, // offset from the top of the image
        w: Int, // width of the selection
        h: Int // height of the selection)
    ) = apply {
        crop = Crop(x, y, w, h)
    }

    /**
     * [ImageUrlBuilder.edgeCrop]
     * Takes 4 parameters: left, top, right, bottom to crop from each edge of the image.
     *
     * @param left - pixels cropped from left of image
     * @param top - pixels cropped from top of image
     * @param right - pixels cropped from right of image
     * @param bottom - pixels cropped from bottom of image
     */
    fun edgeCrop(
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) = apply {
        edgeCrop = EdgeCrop(left, top, right, bottom)
    }

    /**
     * [ImageUrlBuilder.preCrop]
     *
     * Same as [ImageUrlBuilder.crop] except it does the crop before any resize event.
     * Therefore the coordinates are relative to the original image size.
     *
     * @param x - offset from the top left of the image
     * @param y - offset from the top of the image
     * @param w - width of the selection
     * @param h - height of the selection
     */
    fun preCrop(
        x: Int,
        y: Int,
        w: Int,
        h: Int
    ) = apply {
        preCrop = Crop(x, y, w, h)
    }

    /**
     * [ImageUrlBuilder.preEdgeCrop]
     * Same as [ImageUrlBuilder.edgeCrop] but crops from the edge of the image before any resize event.
     *
     * @param left - pixels cropped from left of image
     * @param top - pixels cropped from top of image
     * @param right - pixels cropped from right of image
     * @param bottom - pixels cropped from bottom of image
     */
    fun preEdgeCrop(
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) = apply {
        preEdgeCrop = EdgeCrop(left, top, right, bottom)
    }

    /**
     * [ImageUrlBuilder.rotate]
     * Rotate an image by a specified number of degrees. The rotate is applied after the effect of any resize parameters.
     *
     * @param angle
     */
    fun rotate(angle: Int) {
        rotateDegrees = angle
        preRotate = false
    }

    /**
     * [ImageUrlBuilder.rotate]
     * Rotate an image by a specified number of degrees. The rotate is applied after the effect of any resize parameters.
     *
     * Rotating by an angle that is not a multiple of 90 degrees will expose some pixels underneath the image. You can specify a color for the exposed pixels by specifying an r, g and b value
     *
     * @param angle
     * @param r - the red value in the background RGB
     * @param g - the green value in the background RGB
     * @param b - the blue value in the background RGB
     */
    fun rotate(angle: Int, r: Int, g: Int, b: Int) {
        rotateDegrees = angle
        rgb = Triple(r, g, b)
        preRotate = false
    }

    /**
     * [ImageUrlBuilder.preRotate]
     * The same as [ImageUrlBuilder.rotate], except that the rotation is applied before any resize parameters.
     *
     * @param angle
     */
    fun preRotate(angle: Int) {
        rotateDegrees = angle
        preRotate = true
    }

    /**
     * [ImageUrlBuilder.preRotate]
     * The same as [ImageUrlBuilder.rotate], except that the rotation is applied before any resize parameters.
     *
     * Rotating by an angle that is not a multiple of 90 degrees will expose some pixels underneath the image. You can specify a color for the exposed pixels by specifying an r, g and b valu
     *
     * @param angle
     * @param r - the red value in the background RGB
     * @param g - the green value in the background RGB
     * @param b - the blue value in the background RGB
     */
    fun preRotate(angle: Int, r: Int, g: Int, b: Int) {
        rotateDegrees = angle
        rgb = Triple(r, g, b)
        preRotate = true
    }

    /**
     * [ImageUrlBuilder.flipHorizontally]
     * Flips the image horizontally
     */
    fun flipHorizontally() = apply { flipH = true }

    /**
     * [ImageUrlBuilder.flipVertically]
     * Flips the image vertically
     */
    fun flipVertically() = apply { flipV = true }

    /**
     * [ImageUrlBuilder.dpi]
     * Changes the image resolution. If this param is not specified the image will be returned in the DPI it was originally created in.
     *
     * Optionally specify the resampling algorithm to use when changing the DPI.
     *   - [DpiFilter.Quadratic]
     *   - [DpiFilter.Sinc]
     *   - [DpiFilter.Lanczos] (default)
     *   - [DpiFilter.Point]
     *   - [DpiFilter.Cubic]
     *
     * @param dpi - dots per inch
     * @param dpiFilter (optional) - [DpiFilter]
     */
    @JvmOverloads
    fun dpi(dpi: Int, dpiFilter: DpiFilter? = null) = apply {
        this.dpi = dpi
        this.dpiFilter = dpiFilter
    }

    /**
     * [ImageUrlBuilder.strip]
     * Removes commands and meta data from the image.
     */
    fun strip() = apply { strip = true }

    /**
     * [ImageUrlBuilder.chromaSubsampling]
     * Chroma subsampling is a process which bases image sampling on brightness rather than colour to take advantage of how the human eye works. Generally this affects colours and contrasts such as red and black.
     * Dynamic Media has Chroma Subsampling turned on by default to enhance performance and lower file size media. Use this method to turn it off.
     * Note that turning this off will result in your media being served at a larger file size, so you may wish to adjust the quality setting to compensate
     *
     * @param chromaSubsampling - turn subsampling on or off
     */
    fun chromaSubsampling(chromaSubsampling: Boolean) =
        apply { this.chromaSubsampling = chromaSubsampling }

    /**
     * [ImageUrlBuilder.colorSpace]
     * Changes the colorspace used for the image.
     * @see [ColorSpace] for supported values
     *
     * @param colorSpace
     */
    fun colorSpace(colorSpace: ColorSpace) = apply { this.colorSpace = colorSpace }

    /**
     * [ImageUrlBuilder.sharpen]
     * Sharpens the image with an unsharp mask.
     * @see [Unsharp] for parameter values
     *
     * @param unsharp
     */
    fun sharpen(unsharp: Unsharp) = apply { this.unsharp = unsharp }

    /**
     * [ImageUrlBuilder.compositeMode]
     * When an image is applied on top of a background colour this defines the composite operator.
     * @see [CompositeMode] for parameter values
     *
     * @param compositeMode
     */
    fun compositeMode(compositeMode: CompositeMode) = apply { this.compositeMode = compositeMode }

    /**
     * [ImageUrlBuilder.background]
     * This will set the background colour of the image. It only has an effect when the image is padded or the original image is transparent.
     *
     * @param red the red value of the rgb 0-255
     * @param green the green value of the rgb 0-255
     * @param blue the blue value of the rgb 0-255
     */
    fun background(red: Int, green: Int, blue: Int) = apply {
        backgroundRgb = Triple(red, green, blue)
    }

    /**
     * [ImageUrlBuilder.index]
     * Specify if the PNG image should be indexed. Indexed PNGs have a color palette rather than
     * storing color information with the pixel data.
     *
     * @param indexed true or false
     * @param paletteSize (optional) - Sets the color palette size for a png image. The value must be between 1 and 256. Requires [indexed] to be true
     * The default palette size is 256. Note the image has to be served in png format. Should notably reduce the filesize of an asset.
     */
    fun index(indexed: Boolean, paletteSize: Int? = null) = apply {
        this.indexed = indexed
        this.paletteSize = paletteSize
    }

    /**
     * [ImageUrlBuilder.dither]
     * Dither = false disables dithering. Dithering helps smooth out the colour banding when dealing with a reduced colour palette. Dithering may increase filesize due to increased difficulty when compressing.
     *
     * @param dithered true or false (true by default)
     */
    fun dither(dithered: Boolean) = apply { this.dithered = dithered }

    /**
     * [ImageUrlBuilder.blur]
     * Applies a Gaussian blur to the image.
     *
     * @param radius the accuracy of the blur. Should generally be 2 to 3 times sigma
     * @param sigma the strength of the blur. Both radius and sigma are between 0 to
     */
    fun blur(radius: Int, sigma: Int) = apply { blur = Blur(radius, sigma) }

    /**
     * [ImageUrlBuilder.reduceNoise]
     * Removes noise from an image. The value is between 0 and 5. The higher the value the more noise is removed.
     *
     * @param reduceNoise 0 - 5
     */
    fun reduceNoise(reduceNoise: Int) = apply { this.reduceNoise = reduceNoise }

    /**
     * [ImageUrlBuilder.gamma]
     * Adjusts the gamma correction of an image. gamma is specified as a floating point value. A value of less than 1 will make the image darker and greater than 1 will make the image lighter.
     *
     * @param gamma
     */
    fun gamma(gamma: Float) = apply { this.gamma = gamma }

    /**
     * [ImageUrlBuilder.hsb]
     * Hue, saturation, brightness. Each of these parameters can be specified separately. Each value is between -100 to 100.
     *
     * @param hue - adjust the color of the image. The color change is based on the hue scale
     * @param saturation - adjust the amount of gray in the image
     * @param brightness - adjust the brightness of the image.
     */
    fun hsb(hue: Int, saturation: Int, brightness: Int) = apply {
        this.hue = hue
        this.saturation = saturation
        this.brightness = brightness
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
        if (crop != null) addQuery(
            "crop",
            crop!!.x,
            crop!!.y,
            crop!!.w,
            crop!!.h
        )
        if (edgeCrop != null) addQuery(
            "ecrop",
            edgeCrop!!.left,
            edgeCrop!!.top,
            edgeCrop!!.right,
            edgeCrop!!.bottom
        )

        if (preCrop != null) addQuery(
            "pcrop",
            preCrop!!.x,
            preCrop!!.y,
            preCrop!!.w,
            preCrop!!.h
        )

        if (preEdgeCrop != null) addQuery(
            "pecrop",
            preEdgeCrop!!.left,
            preEdgeCrop!!.top,
            preEdgeCrop!!.right,
            preEdgeCrop!!.bottom
        )

        if (rotateDegrees != null) {
            if (preRotate == true) {
                addQuery("protate", rotateDegrees!!)
            } else {
                addQuery("rotate", rotateDegrees!!)
            }
            if (rgb != null) builder.append(",rgb(${rgb!!.first},${rgb!!.second},${rgb!!.third})")
        }

        if (flipH) addQuery("fliph", true)
        if (flipV) addQuery("flipv", true)
        if (format != null) addQuery("fmt", format!!)
        if (formatQuality != null) addQuery(
            formatQuality!!.queryString,
            formatQuality!!.quality
        )

        if (dpi != null) {
            addQuery("dpi", dpi!!)
            if (dpiFilter != null) addQuery("dpiFilter", dpiFilter!!)
        }

        if (strip) addQuery("strip", true)
        if (!chromaSubsampling) addQuery("fmt.jpeg.chroma", 1, 1, 1)
        if (colorSpace != null) addQuery("cs", colorSpace!!)
        if (unsharp != null) addQuery(
            "unsharp",
            unsharp!!.radius,
            unsharp!!.sigma,
            unsharp!!.amount,
            unsharp!!.threshold
        )

        if (compositeMode != null) addQuery("cm", compositeMode!!)
        if (backgroundRgb != null) addQuery(
            "bg",
            "rgb(${backgroundRgb!!.first},${backgroundRgb!!.second},${backgroundRgb!!.third})",
            preEncoded = true
        )

        if (indexed) {
            addQuery("fmt.png.indexed", true)
            if (paletteSize != null) addQuery("fmt.png.palettesize", paletteSize!!)
        }

        if (!dithered) addQuery("fmt.png.dither", false)
        if (blur != null) addQuery("blur", blur!!.radius, blur!!.sigma)
        if (reduceNoise != null) addQuery("noiser", reduceNoise!!)
        if (gamma != null) addQuery("gamma", gamma!!)
        if (hue != null) addQuery("hue", hue!!)
        if (saturation != null) addQuery("sat", saturation!!)
        if (brightness != null) addQuery("bri", brightness!!)

        return builder.toString()
    }
}
