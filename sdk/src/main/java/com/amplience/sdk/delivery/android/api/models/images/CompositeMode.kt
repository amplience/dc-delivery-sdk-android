package com.amplience.sdk.delivery.android.api.models.images

enum class CompositeMode {
    Over, // default
    Colo,
    Dark,
    Diff,
    Light,
    Multi,
    Cout,
    Cover;

    override fun toString(): String = when (this) {
        Over -> "over"
        Colo -> "colo"
        Dark -> "dark"
        Diff -> "diff"
        Light -> "light"
        Multi -> "multi"
        Cout -> "cout"
        Cover -> "cover"
    }
}
