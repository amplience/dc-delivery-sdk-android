package com.amplience.sampleapp.model

import com.amplience.ampliencesdk.media.Image
import com.google.gson.annotations.SerializedName

data class Banner(
    val background: Image,
    val headline: String,
    val strapline: String,
    @SerializedName("calltoactiontext")
    val callToActionText: String,
    @SerializedName("calltoactionurl")
    val callToActionUrl: String
)
