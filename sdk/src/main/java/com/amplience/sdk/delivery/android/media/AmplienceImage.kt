package com.amplience.sdk.delivery.android.media

import com.amplience.sdk.delivery.android.api.models.images.ImageUrlBuilder

abstract class AmplienceImage {
    abstract val id: String
    abstract val name: String
    abstract val endpoint: String
    abstract val defaultHost: String

    /**
     * [getUrl] returns a url that can be used with any image loading libraries
     *
     * @param builder (optional) - manipulate the image. See [ImageUrlBuilder] for more info
     */
    @JvmOverloads
    fun getUrl(
        builder: ImageUrlBuilder.() -> Unit = {}
    ): String {
        var string = "https://$defaultHost/i/$endpoint/$name"
        string += ImageUrlBuilder().apply(builder).build()
        return string
    }
}
