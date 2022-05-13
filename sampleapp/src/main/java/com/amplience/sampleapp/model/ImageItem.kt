package com.amplience.sampleapp.model

import com.amplience.ampliencesdk.AmplienceManager
import com.amplience.ampliencesdk.media.Image

data class ImageItem(
    val image: Image,
    val altText: String
)
