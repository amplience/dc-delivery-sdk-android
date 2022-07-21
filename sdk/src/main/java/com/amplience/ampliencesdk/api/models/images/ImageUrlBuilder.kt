package com.amplience.ampliencesdk.api.models.images

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
    private var layers: ArrayList<Layer> = ArrayList()

    fun width(width: Int) = apply { this.width = width }
    fun height(height: Int) = apply { this.height = height }
    fun maxWidth(maxWidth: Int) = apply { this.maxWidth = maxWidth }
    fun maxHeight(maxHeight: Int) = apply { this.maxHeight = maxHeight }
    fun quality(quality: Int) = apply { this.quality = quality }
    fun defaultQuality() = apply {
        this.defaultQuality = true
        quality = null
    }

    fun scaleMode(scaleMode: ScaleMode) = apply {
        this.scaleMode = scaleMode
        if (scaleMode is ScaleMode.Clamp) {
            width = scaleMode.width
            height = scaleMode.height
        }
    }

    fun scaleFit(scaleFit: ScaleFit) = apply { this.scaleFit = scaleFit }
    fun resize(resizeAlgorithm: ResizeAlgorithm) = apply { this.resizeAlgorithm = resizeAlgorithm }
    fun upscale(upscale: Upscale) = apply { this.upscale = upscale }

    /**
     * @param format - choose from 6 [ContentFormat]s
     * @param quality - select a quality percentage 0-100 (does not apply to [ContentFormat.Gif] or [ContentFormat.Bmp])
     */
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

    fun crop(
        x: Int, // offset from the top left of the image
        y: Int, // offset from the top of the image
        w: Int, // width of the selection
        h: Int // height of the selection)
    ) = apply {
        crop = Crop(x, y, w, h)
    }

    fun edgeCrop(
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) = apply {
        edgeCrop = EdgeCrop(left, top, right, bottom)
    }

    fun preCrop(
        x: Int, // offset from the top left of the image
        y: Int, // offset from the top of the image
        w: Int, // width of the selection
        h: Int // height of the selection)
    ) = apply {
        preCrop = Crop(x, y, w, h)
    }

    fun preEdgeCrop(
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) = apply {
        preEdgeCrop = EdgeCrop(left, top, right, bottom)
    }

    fun rotate(angle: Int) {
        rotateDegrees = angle
        preRotate = false
    }

    fun rotate(angle: Int, r: Int, g: Int, b: Int) {
        rotateDegrees = angle
        rgb = Triple(r, g, b)
        preRotate = false
    }

    fun preRotate(angle: Int) {
        rotateDegrees = angle
        preRotate = true
    }

    fun preRotate(angle: Int, r: Int, g: Int, b: Int) {
        rotateDegrees = angle
        rgb = Triple(r, g, b)
        preRotate = true
    }

    fun flipHorizontally() = apply { flipH = true }
    fun flipVertically() = apply { flipV = true }

    fun dpi(dpi: Int, dpiFilter: DpiFilter? = null) = apply {
        this.dpi = dpi
        this.dpiFilter = dpiFilter
    }

    fun strip() = apply { strip = true }

    fun chromaSubsampling(chromaSubsampling: Boolean) =
        apply { this.chromaSubsampling = chromaSubsampling }

    fun colorSpace(colorSpace: ColorSpace) = apply { this.colorSpace = colorSpace }
    fun unsharp(unsharp: Unsharp) = apply { this.unsharp = unsharp }
    fun compositeMode(compositeMode: CompositeMode) = apply { this.compositeMode = compositeMode }
    fun background(red: Int, green: Int, blue: Int) = apply {
        backgroundRgb = Triple(red, green, blue)
    }

    /**
     * Specify if the PNG image should be indexed. Indexed PNGs have a color palette rather than
     * storing color information with the pixel data.
     */
    fun index(indexed: Boolean, paletteSize: Int? = null) = apply {
        this.indexed = indexed
        this.paletteSize = paletteSize
    }

    fun dither(dithered: Boolean) = apply { this.dithered = dithered }
    fun blur(radius: Int, sigma: Int) = apply { blur = Blur(radius, sigma) }
    fun reduceNoise(reduceNoise: Int) = apply { this.reduceNoise = reduceNoise }
    fun gamma(gamma: Float) = apply { this.gamma = gamma }
    fun hsb(hue: Int, saturation: Int, brightness: Int) = apply {
        this.hue = hue
        this.saturation = saturation
        this.brightness = brightness
    }

    fun addImageLayer(
        src: String,
        width: Int? = null,
        height: Int? = null,
        topPx: Int? = null,
        topPercent: Int? = null,
        leftPx: Int? = null,
        leftPercent: Int? = null,
        bottomPx: Int? = null,
        bottomPercent: Int? = null,
        rightPx: Int? = null,
        rightPercent: Int? = null,
        anchor: Anchor? = null,
        opacity: Int? = null
    ) {
        layers.add(
            ImageLayer(
                src,
                width,
                height,
                topPx,
                topPercent,
                leftPx,
                leftPercent,
                bottomPx,
                bottomPercent,
                rightPx,
                rightPercent,
                anchor,
                opacity
            )
        )
    }

    fun addTextLayer(
        text: String,
        fontSize: Int? = null, // defaults to 10
        fontFamily: String? = null, // If you don't specify a font family, or specify a font that is not installed on your account, then it will default to Helevetica.
        fontStyle: TextLayer.FontStyle? = null,
        fontWeight: Int? = null, // Valid values are from 100 to 900 in multiples of 100.
        fontStretch: TextLayer.FontStretch? = null,
        textColor: TextLayer.TextColor? = null,
        textDecoration: TextLayer.Decoration? = null,
        textAlign: String? = null
    ) {
        layers.add(
            TextLayer(
                text,
                fontSize,
                fontFamily,
                fontStyle,
                fontWeight,
                fontStretch,
                textColor,
                textDecoration,
                textAlign
            )
        )
    }

    fun build(): String {
        val builder = StringBuilder()
        var firstQuery = true

        fun addQuery(queryName: String, query: String) {
            if (firstQuery) {
                builder.append("?")
                firstQuery = false
            } else {
                builder.append("&")
            }
            builder.append("$queryName=")
            builder.append(URLEncoder.encode(query, "UTF-8"))
        }

        if (width != null) addQuery("w", "$width")
        if (height != null) addQuery("h", "$height")
        if (maxWidth != null) addQuery("maxW", "$maxWidth")
        if (maxHeight != null) addQuery("maxH", "$maxHeight")
        if (scaleMode != null) addQuery("sm", "$scaleMode")
        if (scaleFit != null) addQuery("scalefit", "$scaleFit")
        if (resizeAlgorithm != null) addQuery("filter", "$resizeAlgorithm")
        if (upscale != null) addQuery("upscale", "$upscale")
        if (crop != null) addQuery("crop", "${crop!!.x},${crop!!.y},${crop!!.w},${crop!!.h}")
        if (edgeCrop != null)
            addQuery("ecrop", "${edgeCrop!!.left},${edgeCrop!!.top},${edgeCrop!!.right},${edgeCrop!!.bottom}")

        if (preCrop != null)
            addQuery("pcrop", "${preCrop!!.x},${preCrop!!.y},${preCrop!!.w},${preCrop!!.h}")

        if (preEdgeCrop != null)
            addQuery("pecrop", "${preEdgeCrop!!.left},${preEdgeCrop!!.top},${preEdgeCrop!!.right},${preEdgeCrop!!.bottom}")

        if (rotateDegrees != null) {
            if (preRotate == true) {
                addQuery("protate", "$rotateDegrees")
            } else {
                addQuery("rotate", "$rotateDegrees")
            }
            if (rgb != null) builder.append(",rgb(${rgb!!.first},${rgb!!.second},${rgb!!.third})")
        }

        if (flipH) addQuery("fliph", "true")
        if (flipV) addQuery("flipv"," true")
        if (format != null) addQuery("fmt", "$format")
        if (formatQuality != null) addQuery(formatQuality!!.queryString, "${formatQuality!!.quality}")

        if (dpi != null) {
            addQuery("dpi", "$dpi")
            if (dpiFilter != null) addQuery("dpiFilter", "$dpiFilter")
        }

        if (strip) addQuery("strip", "true")
        if (!chromaSubsampling) addQuery("fmt.jpeg.chroma", "1,1,1")
        if (colorSpace != null) addQuery("cs", "$colorSpace")
        if (unsharp != null)
            addQuery("unsharp", "${unsharp!!.radius},${unsharp!!.sigma},${unsharp!!.amount},${unsharp!!.threshold}")

        if (compositeMode != null) addQuery("cm", "$compositeMode")
        if (backgroundRgb != null)
            addQuery("bg", "rgb(${backgroundRgb!!.first},${backgroundRgb!!.second},${backgroundRgb!!.third})")

        if (indexed) {
            addQuery("fmt.png.indexed", "true")
            if (paletteSize != null) addQuery("fmt.png.palettesize", "$paletteSize")
        }

        if (!dithered) addQuery("fmt.png.dither", "false")
        if (blur != null) addQuery("blur", "${blur!!.radius},${blur!!.sigma}")
        if (reduceNoise != null) addQuery("noiser", "$reduceNoise")
        if (gamma != null) addQuery("gamma", "$gamma")
        if (hue != null) addQuery("hue", "$hue")
        if (saturation != null) addQuery("sat", "$saturation")
        if (brightness != null) addQuery("bri", "$brightness")

        layers.forEachIndexed { index, layer ->
            addQuery("layer${index + 1}", layer.toQuery())
        }

        return builder.toString()
    }
}
