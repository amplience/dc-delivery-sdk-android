package com.amplience.sdk.delivery.android.api.models.images

enum class ContentFormat {
    Webp, Jp2, Jpeg, Png, Gif, Bmp;

    override fun toString(): String = when (this) {
        Webp -> "webp"
        Jp2 -> "jp2"
        Jpeg -> "jpeg"
        Png -> "png"
        Gif -> "gif"
        Bmp -> "bmp"
    }
}
