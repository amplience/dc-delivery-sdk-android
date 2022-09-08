package com.amplience.sdk.delivery.android.api.models.images

enum class ResizeAlgorithm {
    Quadratic,
    Sinc,
    Lanczos, // Default
    Point,
    Cubic,
    Hermite;

    override fun toString(): String = when (this) {
        Quadratic -> "q"
        Sinc -> "s"
        Lanczos -> "l"
        Point -> "p"
        Cubic -> "c"
        Hermite -> "h"
    }
}
