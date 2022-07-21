package com.amplience.sampleapp.model

import com.amplience.ampliencesdk.media.AmplienceVideo

data class Video(
    override val id: String,
    override val name: String,
    override val endpoint: String,
    override val defaultHost: String
) : AmplienceVideo()
