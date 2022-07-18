package com.amplience.ampliencesdk.media

import com.amplience.ampliencesdk.ContentClient
import com.amplience.ampliencesdk.api.models.images.ImageUrlBuilder

abstract class AmplienceImage {
    abstract val id: String
    abstract val name: String
    abstract val endpoint: String
    abstract val defaultHost: String

    fun getUrl(builder: ImageUrlBuilder.() -> Unit = {}) =
        ContentClient.getInstance().getImageUrl(this, builder)
}
