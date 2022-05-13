package com.amplience.ampliencesdk.api.models.images

sealed class FormatQuality {
    class Webp(val quality: Int): FormatQuality()
    class Jp2(val quality: Int): FormatQuality()
    class Jpeg(val quality: Int): FormatQuality()
    class Png(val quality: Int): FormatQuality()

    override fun toString(): String = when (this) {
        is Webp -> "fmt.webp.qlt=$quality"
        is Jp2 -> "fmt.jp2.qlt=$quality"
        is Png -> "fmt.png.qlt=$quality"
        is Jpeg -> "fmt.jpeg.qlt=$quality"
    }
}
