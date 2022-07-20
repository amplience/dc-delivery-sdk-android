package com.amplience.ampliencesdk.media

import com.amplience.ampliencesdk.api.models.images.ImageUrlBuilder

abstract class AmplienceVideo {
    abstract val name: String
    abstract val defaultHost: String
    abstract val endpoint: String

    /**
     * [getUrl] returns a url that can be used with any video loading libraries
     *
     * @param videoProfile
     */
    fun getUrl(
        videoProfile: String = "mp4_720p"
    ): String = "https://${defaultHost}/v/${endpoint}/${name}/$videoProfile"

    /**
     * [getThumbnailUrl] returns a url that can be used with any image loading libraries
     *
     * @param builder (optional) - manipulate the thumbnail image. See [ImageUrlBuilder] for more info
     * @param thumbName (optional) - the specific thumb frame
     *     e.g. https://cdn.media.amplience.net/v/ampproduct/ski-collection/thumbs/frame_0020.png
     */
    fun getThumbnailUrl(
        builder: ImageUrlBuilder.() -> Unit = {},
        thumbName: String? = null
    ): String {
        var string = "https://${defaultHost}/v/${endpoint}/${name}"
        if (thumbName != null) string += "/thumbs/$thumbName"
        string += ImageUrlBuilder().apply(builder).build()
        return string
    }
}
