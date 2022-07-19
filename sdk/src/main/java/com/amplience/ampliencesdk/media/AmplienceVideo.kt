package com.amplience.ampliencesdk.media

import com.amplience.ampliencesdk.api.models.images.ImageUrlBuilder

abstract class AmplienceVideo {
    abstract val name: String
    abstract val defaultHost: String
    abstract val endpoint: String

    /**
     * [getUrl] returns a url that can be used with any video loading libraries
     *
     * @param video - your implementation of an [AmplienceVideo]
     */
    fun getUrl(
        video: AmplienceVideo
    ): String = "https://${video.defaultHost}/v/${video.endpoint}/${video.name}/mp4_720p"

    /**
     * [getThumbnailUrl] returns a url that can be used with any image loading libraries
     *
     * @param video - your implementation of an [AmplienceVideo]
     * @param builder (optional) - manipulate the thumbnail image. See [ImageUrlBuilder] for more info
     * @param thumbName (optional) - the specific thumb frame
     *     e.g. https://cdn.media.amplience.net/v/ampproduct/ski-collection/thumbs/frame_0020.png
     */
    fun getThumbnailUrl(
        video: AmplienceVideo,
        builder: ImageUrlBuilder.() -> Unit = {},
        thumbName: String? = null
    ): String {
        var string = "https://${video.defaultHost}/v/${video.endpoint}/${video.name}"
        if (thumbName != null) string += "/thumbs/$thumbName"
        string += ImageUrlBuilder().apply(builder).build()
        return string
    }
}
