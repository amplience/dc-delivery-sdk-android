package com.amplience.sdk.delivery.android.api.models.images

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
