package com.amplience.ampliencesdk.media

enum class VideoProfile {
    MP4_720P, MP4_480P;

    override fun toString() = when (this) {
        MP4_720P -> "mp4_720p"
        MP4_480P -> "mp4_480p"
    }
}
