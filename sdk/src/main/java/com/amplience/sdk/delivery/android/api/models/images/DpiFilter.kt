package com.amplience.sdk.delivery.android.api.models.images

enum class DpiFilter {
    Quadratic,
    Sinc,
    Lanczos, // Default
    Point,
    Cubic;

    override fun toString(): String = when (this) {
        Quadratic -> "q"
        Sinc -> "s"
        Lanczos -> "l"
        Point -> "p"
        Cubic -> "c"
    }
}
