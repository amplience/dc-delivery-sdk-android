package com.amplience.sampleapp.model

import com.amplience.ampliencesdk.media.AmplienceImage
import com.google.gson.annotations.SerializedName

data class Banner(
    val background: Image,
    val headline: String,
    val strapline: String,
    val link: Link
)
