package com.amplience.ampliencesdk.api.models.images

enum class Upscale {
    True,
    False,
    Padd;

    override fun toString(): String = when (this) {
        True -> "true"
        False -> "false"
        Padd -> "padd"
    }
}
