package com.amplience.ampliencesdk.api.models.images

sealed class FormatQuality(val quality: Int) {
    class Webp(quality: Int) : FormatQuality(quality)
    class Jp2(quality: Int) : FormatQuality(quality)
    class Jpeg(quality: Int) : FormatQuality(quality)
    class Png(quality: Int) : FormatQuality(quality)

    val queryString: String
        get() = when (this) {
            is Jp2 -> "fmt.jp2.qlt"
            is Jpeg -> "fmt.jpeg.qlt"
            is Png -> "fmt.png.qlt"
            is Webp -> "fmt.webp.qlt"
        }
}
