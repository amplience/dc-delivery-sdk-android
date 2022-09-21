package com.amplience.sdk.delivery.android

import com.amplience.sdk.delivery.android.api.models.images.ColorSpace
import com.amplience.sdk.delivery.android.api.models.images.CompositeMode
import com.amplience.sdk.delivery.android.api.models.images.ContentFormat
import com.amplience.sdk.delivery.android.api.models.images.DpiFilter
import com.amplience.sdk.delivery.android.api.models.images.ResizeAlgorithm
import com.amplience.sdk.delivery.android.api.models.images.ScaleMode
import com.amplience.sdk.delivery.android.api.models.images.Unsharp
import com.amplience.sdk.delivery.android.media.AmplienceImage
import org.junit.Assert.assertEquals
import org.junit.Test

class ImageUrlUnitTest {

    private val testImage = object : AmplienceImage() {
        override val id: String = "1"
        override val name: String = "test-image"
        override val endpoint: String
            get() = "ampproduct"
        override val defaultHost: String
            get() = "cdn.media.amplience.net"
    }

    @Test
    fun `create basic image url`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image",
            testImage.getUrl()
        )
    }

    @Test
    fun `create image url with width`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?w=200",
            testImage.getUrl { width(200) }
        )
    }

    @Test
    fun `create image url with height`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?h=200",
            testImage.getUrl { height(200) }
        )
    }

    @Test
    fun `create image url with width and height`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?w=200&h=200",
            testImage.getUrl {
                width(200)
                height(200)
            }
        )
    }

    @Test
    fun `create image url with max width`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?maxW=200",
            testImage.getUrl { maxWidth(200) }
        )
    }

    @Test
    fun `create image url with max height`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?maxH=200",
            testImage.getUrl { maxHeight(200) }
        )
    }

    @Test
    fun `create image url with max scale mode crop to fit`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?sm=c",
            testImage.getUrl { scaleMode(ScaleMode.CropToFit) }
        )
    }

    @Test
    fun `create image url with max scale mode stretch`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?sm=s",
            testImage.getUrl { scaleMode(ScaleMode.Stretch) }
        )
    }

    @Test
    fun `create image url with max scale mode top left`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?sm=tl",
            testImage.getUrl { scaleMode(ScaleMode.TopLeft) }
        )
    }

    @Test
    fun `create image url with max scale mode top center`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?sm=tc",
            testImage.getUrl { scaleMode(ScaleMode.TopCenter) }
        )
    }

    @Test
    fun `create image url with max scale mode top right`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?sm=tr",
            testImage.getUrl { scaleMode(ScaleMode.TopRight) }
        )
    }

    @Test
    fun `create image url with max scale mode middle left`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?sm=ml",
            testImage.getUrl { scaleMode(ScaleMode.MiddleLeft) }
        )
    }

    @Test
    fun `create image url with max scale mode middle center`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?sm=mc",
            testImage.getUrl { scaleMode(ScaleMode.MiddleCenter) }
        )
    }

    @Test
    fun `create image url with max scale mode middle right`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?sm=mr",
            testImage.getUrl { scaleMode(ScaleMode.MiddleRight) }
        )
    }

    @Test
    fun `create image url with max scale mode bottom left`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?sm=bl",
            testImage.getUrl { scaleMode(ScaleMode.BottomLeft) }
        )
    }

    @Test
    fun `create image url with max scale mode bottom center`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?sm=bc",
            testImage.getUrl { scaleMode(ScaleMode.BottomCenter) }
        )
    }

    @Test
    fun `create image url with max scale mode bottom right`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?sm=br",
            testImage.getUrl { scaleMode(ScaleMode.BottomRight) }
        )
    }

    @Test
    fun `create image url with max scale mode aspect ratio`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?sm=aspect&aspect=16:9",
            testImage.getUrl {
                scaleMode(ScaleMode.AspectRatio(ratio = "16:9"))
            }
        )
    }

    @Test
    fun `create image url with max scale mode edge`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?sm=edge&resize.edge=w&resize.edge.length=200",
            testImage.getUrl { scaleMode(ScaleMode.Edge(ScaleMode.Edge.EdgeType.Width, 200)) }
        )
    }

    @Test
    fun `create image url with max scale mode clamp`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?w=200&h=200&sm=clamp",
            testImage.getUrl { scaleMode(ScaleMode.Clamp(width = 200, height = 200)) }
        )
    }

    @Test
    fun `create image url with resize algorithm quadratic`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?filter=q",
            testImage.getUrl { resize(ResizeAlgorithm.Quadratic) }
        )
    }

    @Test
    fun `create image url with resize algorithm sinc`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?filter=s",
            testImage.getUrl { resize(ResizeAlgorithm.Sinc) }
        )
    }

    @Test
    fun `create image url with resize algorithm lanczos`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?filter=l",
            testImage.getUrl { resize(ResizeAlgorithm.Lanczos) }
        )
    }

    @Test
    fun `create image url with resize algorithm paint`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?filter=p",
            testImage.getUrl { resize(ResizeAlgorithm.Point) }
        )
    }

    @Test
    fun `create image url with resize algorithm cubic`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?filter=c",
            testImage.getUrl { resize(ResizeAlgorithm.Cubic) }
        )
    }

    @Test
    fun `create image url with resize algorithm hermite`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?filter=h",
            testImage.getUrl { resize(ResizeAlgorithm.Hermite) }
        )
    }

    @Test
    fun `create image url with upscale true`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?upscale=true",
            testImage.getUrl { upscale(com.amplience.sdk.delivery.android.api.models.images.Upscale.True) }
        )
    }

    @Test
    fun `create image url with upscale false`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?upscale=false",
            testImage.getUrl { upscale(com.amplience.sdk.delivery.android.api.models.images.Upscale.False) }
        )
    }

    @Test
    fun `create image url with upscale padd`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?upscale=padd",
            testImage.getUrl { upscale(com.amplience.sdk.delivery.android.api.models.images.Upscale.Padd) }
        )
    }

    @Test
    fun `create image url with bitmap format`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?fmt=bmp",
            testImage.getUrl { format(ContentFormat.Bmp) }
        )
    }

    @Test
    fun `create image url with gif format`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?fmt=gif",
            testImage.getUrl { format(ContentFormat.Gif) }
        )
    }

    @Test
    fun `create image url with jpeg format`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?fmt=jpeg",
            testImage.getUrl { format(ContentFormat.Jpeg) }
        )
    }

    @Test
    fun `create image url with 50 percent quality jpeg format`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?fmt=jpeg&fmt.jpeg.qlt=50",
            testImage.getUrl { format(ContentFormat.Jpeg, quality = 50) }
        )
    }

    @Test
    fun `create image url with jp2 format`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?fmt=jp2",
            testImage.getUrl { format(ContentFormat.Jp2) }
        )
    }

    @Test
    fun `create image url with 50 percent quality jp2 format`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?fmt=jp2&fmt.jp2.qlt=50",
            testImage.getUrl { format(ContentFormat.Jp2, quality = 50) }
        )
    }

    @Test
    fun `create image url with png format`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?fmt=png",
            testImage.getUrl { format(ContentFormat.Png) }
        )
    }

    @Test
    fun `create image url with 50 percent quality png format`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?fmt=png&fmt.png.qlt=50",
            testImage.getUrl { format(ContentFormat.Png, quality = 50) }
        )
    }

    @Test
    fun `create image url with webp format`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?fmt=webp",
            testImage.getUrl { format(ContentFormat.Webp) }
        )
    }

    @Test
    fun `create image url with 50 percent quality webp format`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?fmt=webp&fmt.webp.qlt=50",
            testImage.getUrl { format(ContentFormat.Webp, quality = 50) }
        )
    }
}
