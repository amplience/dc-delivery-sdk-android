package com.amplience.ampliencesdk

import com.amplience.ampliencesdk.api.models.images.ColorSpace
import com.amplience.ampliencesdk.api.models.images.CompositeMode
import com.amplience.ampliencesdk.api.models.images.ContentFormat
import com.amplience.ampliencesdk.api.models.images.DpiFilter
import com.amplience.ampliencesdk.api.models.images.ResizeAlgorithm
import com.amplience.ampliencesdk.api.models.images.ScaleMode
import com.amplience.ampliencesdk.api.models.images.Unsharp
import com.amplience.ampliencesdk.api.models.images.Upscale
import com.amplience.ampliencesdk.media.AmplienceImage
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
            testImage.getUrl { upscale(Upscale.True) }
        )
    }

    @Test
    fun `create image url with upscale false`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?upscale=false",
            testImage.getUrl { upscale(Upscale.False) }
        )
    }

    @Test
    fun `create image url with upscale padd`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?upscale=padd",
            testImage.getUrl { upscale(Upscale.Padd) }
        )
    }

    @Test
    fun `create image url with crop`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?crop=10,20,30,40",
            testImage.getUrl { crop(x = 10, y = 20, w = 30, h = 40) }
        )
    }

    @Test
    fun `create image url with edge crop`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?ecrop=10,20,30,40",
            testImage.getUrl { edgeCrop(left = 10, top = 20, right = 30, bottom = 40) }
        )
    }

    @Test
    fun `create image url with pre crop`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?pcrop=10,20,30,40",
            testImage.getUrl { preCrop(x = 10, y = 20, w = 30, h = 40) }
        )
    }

    @Test
    fun `create image url with pre edge crop`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?pecrop=10,20,30,40",
            testImage.getUrl { preEdgeCrop(left = 10, top = 20, right = 30, bottom = 40) }
        )
    }

    @Test
    fun `create image url with rotate`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?rotate=45",
            testImage.getUrl { rotate(angle = 45) }
        )
    }

    @Test
    fun `create image url with rotate and green bg`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?rotate=45,rgb(0,255,0)",
            testImage.getUrl { rotate(angle = 45, r = 0, g = 255, b = 0) }
        )
    }

    @Test
    fun `create image url with pre rotate`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?protate=45",
            testImage.getUrl { preRotate(angle = 45) }
        )
    }

    @Test
    fun `create image url with pre rotate and green bg`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?protate=45,rgb(0,255,0)",
            testImage.getUrl { preRotate(angle = 45, r = 0, g = 255, b = 0) }
        )
    }

    @Test
    fun `create image url with horizontal flip`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?fliph=true",
            testImage.getUrl { flipHorizontally() }
        )
    }

    @Test
    fun `create image url with vertical flip`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?flipv=true",
            testImage.getUrl { flipVertically() }
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

    @Test
    fun `create image url with 50 dpi`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?dpi=50",
            testImage.getUrl { dpi(dpi = 50) }
        )
    }

    @Test
    fun `create image url with 50 dpi and quadratic filter`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?dpi=50&dpiFilter=q",
            testImage.getUrl { dpi(dpi = 50, dpiFilter = DpiFilter.Quadratic) }
        )
    }

    @Test
    fun `create image url with 50 dpi and sinc filter`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?dpi=50&dpiFilter=s",
            testImage.getUrl { dpi(dpi = 50, dpiFilter = DpiFilter.Sinc) }
        )
    }

    @Test
    fun `create image url with 50 dpi and lanczos filter`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?dpi=50&dpiFilter=l",
            testImage.getUrl { dpi(dpi = 50, dpiFilter = DpiFilter.Lanczos) }
        )
    }

    @Test
    fun `create image url with 50 dpi and point filter`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?dpi=50&dpiFilter=p",
            testImage.getUrl { dpi(dpi = 50, dpiFilter = DpiFilter.Point) }
        )
    }

    @Test
    fun `create image url with 50 dpi and cubic filter`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?dpi=50&dpiFilter=c",
            testImage.getUrl { dpi(dpi = 50, dpiFilter = DpiFilter.Cubic) }
        )
    }

    @Test
    fun `create image url with strip`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?strip=true",
            testImage.getUrl { strip() }
        )
    }

    @Test
    fun `create image url with no chroma subsampling`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?fmt.jpeg.chroma=1,1,1",
            testImage.getUrl { chromaSubsampling(false) }
        )
    }

    @Test
    fun `create image url with rgb color space`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?cs=rgb",
            testImage.getUrl { colorSpace(ColorSpace.RGB) }
        )
    }

    @Test
    fun `create image url with RGBA color space`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?cs=rgba",
            testImage.getUrl { colorSpace(ColorSpace.RGBA) }
        )
    }

    @Test
    fun `create image url with SRGB color space`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?cs=srgb",
            testImage.getUrl { colorSpace(ColorSpace.SRGB) }
        )
    }

    @Test
    fun `create image url with gray color space`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?cs=gray",
            testImage.getUrl { colorSpace(ColorSpace.Gray) }
        )
    }

    @Test
    fun `create image url with CMYK color space`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?cs=cmyk",
            testImage.getUrl { colorSpace(ColorSpace.CMYK) }
        )
    }

    @Test
    fun `create image url with OHTA color space`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?cs=ohta",
            testImage.getUrl { colorSpace(ColorSpace.OHTA) }
        )
    }

    @Test
    fun `create image url with LAB color space`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?cs=lab",
            testImage.getUrl { colorSpace(ColorSpace.LAB) }
        )
    }

    @Test
    fun `create image url with XYZ color space`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?cs=xyz",
            testImage.getUrl { colorSpace(ColorSpace.XYZ) }
        )
    }

    @Test
    fun `create image url with HSB color space`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?cs=hsb",
            testImage.getUrl { colorSpace(ColorSpace.HSB) }
        )
    }

    @Test
    fun `create image url with HSL color space`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?cs=hsl",
            testImage.getUrl { colorSpace(ColorSpace.HSL) }
        )
    }

    @Test
    fun `create image url with unsharp`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?unsharp=3,0.05,150,100",
            testImage.getUrl {
                sharpen(Unsharp(radius = 3, sigma = 0.05, amount = 150, threshold = 100))
            }
        )
    }

    @Test
    fun `create image url with composite mode over`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?cm=over",
            testImage.getUrl {
                compositeMode(CompositeMode.Over)
            }
        )
    }

    @Test
    fun `create image url with composite mode colo`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?cm=colo",
            testImage.getUrl {
                compositeMode(CompositeMode.Colo)
            }
        )
    }

    @Test
    fun `create image url with composite mode dark`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?cm=dark",
            testImage.getUrl {
                compositeMode(CompositeMode.Dark)
            }
        )
    }

    @Test
    fun `create image url with composite mode diff`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?cm=diff",
            testImage.getUrl {
                compositeMode(CompositeMode.Diff)
            }
        )
    }

    @Test
    fun `create image url with composite mode light`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?cm=light",
            testImage.getUrl {
                compositeMode(CompositeMode.Light)
            }
        )
    }

    @Test
    fun `create image url with composite mode multi`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?cm=multi",
            testImage.getUrl {
                compositeMode(CompositeMode.Multi)
            }
        )
    }

    @Test
    fun `create image url with composite mode cout`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?cm=cout",
            testImage.getUrl {
                compositeMode(CompositeMode.Cout)
            }
        )
    }

    @Test
    fun `create image url with composite mode cover`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?cm=cover",
            testImage.getUrl {
                compositeMode(CompositeMode.Cover)
            }
        )
    }

    @Test
    fun `create image url with background colour`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?bg=rgb(237,185,227)",
            testImage.getUrl {
                background(red = 237, green = 185, blue = 227)
            }
        )
    }

    @Test
    fun `create image url with index`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?fmt.png.indexed=true",
            testImage.getUrl {
                index(true)
            }
        )
    }

    @Test
    fun `create image url with index and palette size`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?fmt.png.indexed=true&fmt.png.palettesize=30",
            testImage.getUrl {
                index(true, paletteSize = 30)
            }
        )
    }

    @Test
    fun `create image url with no dither`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?fmt.png.dither=false",
            testImage.getUrl {
                dither(false)
            }
        )
    }

    @Test
    fun `create image url with blur`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?blur=30,60",
            testImage.getUrl {
                blur(radius = 30, sigma = 60)
            }
        )
    }

    @Test
    fun `create image url with noise reduction`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?noiser=3",
            testImage.getUrl {
                reduceNoise(3)
            }
        )
    }

    @Test
    fun `create image url with gamma`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?gamma=0.3",
            testImage.getUrl {
                gamma(0.3f)
            }
        )
    }

    @Test
    fun `create image url with hsl values adjusted`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?hue=30&sat=40&bri=50",
            testImage.getUrl {
                hsb(30, 40, 50)
            }
        )
    }

    @Test
    fun `create image url with text layer`() {
        assertEquals(
            "https://cdn.media.amplience.net/i/ampproduct/test-image?layer1=[text=Hello]",
            testImage.getUrl {
                addTextLayer(text = "Hello")
            }
        )
    }
}
