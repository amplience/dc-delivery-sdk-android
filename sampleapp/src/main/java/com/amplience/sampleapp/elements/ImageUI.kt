package com.amplience.sampleapp.elements

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.amplience.ampliencesdk.media.Image
import com.amplience.sampleapp.model.ImageItem
import timber.log.Timber

@Composable
fun ImageUI(image: Image, altText: String?, modifier: Modifier = Modifier) {
    Timber.d("URL ${image.getUrl()}")
    Image(
        painter = rememberAsyncImagePainter(image.getUrl()),
        contentDescription = altText,
        contentScale = ContentScale.FillWidth,
        modifier = modifier
    )
}
