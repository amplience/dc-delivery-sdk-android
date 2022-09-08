package com.amplience.sampleapp.model

import com.amplience.sdk.delivery.android.media.AmplienceVideo

data class Video(
    override val id: String,
    override val name: String,
    override val endpoint: String,
    override val defaultHost: String
) : AmplienceVideo()
