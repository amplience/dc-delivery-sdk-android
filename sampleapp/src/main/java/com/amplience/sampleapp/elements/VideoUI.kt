package com.amplience.sampleapp.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.amplience.ampliencesdk.media.AmplienceVideo
import timber.log.Timber

@Composable
fun VideoUI(video: AmplienceVideo, modifier: Modifier = Modifier) {
    Timber.d("Video $video")
    Timber.d("Url ${video.getThumbnailUrl()}")
    val uriHandler = LocalUriHandler.current
    Box {
        Image(
            painter = rememberAsyncImagePainter(video.getThumbnailUrl()),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = modifier.clickable {
                uriHandler.openUri(video.getUrl())
            }
        )
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Play",
            modifier = Modifier
                .align(Alignment.Center)
                .height(64.dp)
                .width(64.dp),
            tint = Color.White.copy(alpha = 0.8f)
        )
    }
}
