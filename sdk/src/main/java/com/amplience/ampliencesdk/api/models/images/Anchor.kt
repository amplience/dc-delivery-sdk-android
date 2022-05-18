package com.amplience.ampliencesdk.api.models.images

enum class Anchor {
    TopLeft,
    TopCenter,
    TopRight,
    MiddleLeft,
    MiddleCenter,
    MiddleRight,
    BottomLeft,
    BottomCenter,
    BottomRight;

    override fun toString(): String = when (this) {
        TopLeft -> "TL"
        TopCenter -> "TC"
        TopRight -> "TR"
        MiddleLeft -> "ML"
        MiddleCenter -> "MC"
        MiddleRight -> "MR"
        BottomLeft -> "BL"
        BottomCenter -> "BC"
        BottomRight -> "BR"
    }
}
