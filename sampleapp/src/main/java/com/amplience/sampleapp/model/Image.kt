package com.amplience.sampleapp.model

import com.amplience.sdk.delivery.android.media.AmplienceImage

data class Image(
    override val id: String,
    override val name: String,
    override val defaultHost: String,
    override val endpoint: String,
    val alt: String?
) : AmplienceImage()
