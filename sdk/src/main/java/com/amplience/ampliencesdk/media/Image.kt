package com.amplience.ampliencesdk.media

import com.amplience.ampliencesdk.AmplienceManager

data class Image(
    val id: String,
    val name: String,
    val endpoint: String,
    val defaultHost: String
) {
    fun getUrl() = AmplienceManager.getInstance().getImageUrl(this)
}
