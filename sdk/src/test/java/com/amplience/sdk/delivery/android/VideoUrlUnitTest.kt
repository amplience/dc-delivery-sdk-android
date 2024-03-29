package com.amplience.sdk.delivery.android

import com.amplience.sdk.delivery.android.media.AmplienceVideo
import org.junit.Assert.assertEquals
import org.junit.Test

class VideoUrlUnitTest {

    private val testVideo = object : AmplienceVideo() {
        override val id: String = "1"
        override val name: String = "test-video"
        override val endpoint: String
            get() = "ampproduct"
        override val defaultHost: String
            get() = "cdn.media.amplience.net"
    }

    @Test
    fun `create basic video url`() {
        assertEquals(
            "https://cdn.media.amplience.net/v/ampproduct/test-video/mp4_720p",
            testVideo.getUrl()
        )
    }

    @Test
    fun `create video url with custom profile`() {
        assertEquals(
            "https://cdn.media.amplience.net/v/ampproduct/test-video/mp4_480p",
            testVideo.getUrl(videoProfile = "mp4_480p")
        )
    }

    @Test
    fun `create video thumbnail url`() {
        assertEquals(
            "https://cdn.media.amplience.net/v/ampproduct/test-video",
            testVideo.getThumbnailUrl()
        )
    }

    @Test
    fun `create video thumbnail url with custom thumb name`() {
        assertEquals(
            "https://cdn.media.amplience.net/v/ampproduct/test-video/thumbs/thumb01",
            testVideo.getThumbnailUrl(thumbName = "thumb01")
        )
    }
}
