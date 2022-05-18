package com.amplience.ampliencesdk.media

import com.amplience.ampliencesdk.AmplienceManager
import com.amplience.ampliencesdk.api.models.images.ImageUrlBuilder

abstract class AmplienceImage {
    abstract val id: String
    abstract val name: String
    abstract val endpoint: String
    abstract val defaultHost: String

    fun getUrl(builder: ImageUrlBuilder.() -> Unit = {}) =
        AmplienceManager.getInstance().getImageUrl(this, builder)
}
