package com.amplience.sampleapp.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amplience.sampleapp.model.Banner
import com.amplience.sampleapp.model.Image
import com.amplience.sampleapp.model.Link

@Composable
fun Banner(banner: Banner, modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current

    Box(modifier) {
        ImageUI(
            image = banner.background.image,
            banner.background.image.alt,
            Modifier.fillMaxWidth()
        )

        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column {
                LightHeadline(headline = banner.headline)
                LightSubheading(subheading = banner.strapline)
            }

            CallToAction(
                banner.link.title,
                onClick = {
                    uriHandler.openUri(banner.link.url)
                },
                Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}

@Composable
private fun CallToAction(
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier
) {
    TextButton(
        onClick = onClick,
        modifier
            .background(Color.DarkGray.copy(alpha = 0.8f))
    ) {
        Text(text = buttonText, color = Color.White)
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Preview
@Composable
fun BannerPreview() {
    val banner = Banner(
        background = Banner.BackgroundImage(image = Image("", "", "", "", "")),
        headline = "Get the look",
        strapline = "A chance to update your wardrobe",
        link = Link("Buy now", "https://google.com")
    )

    Banner(banner = banner)
}
