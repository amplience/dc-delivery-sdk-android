package com.amplience.sdk.delivery.android.api.models

enum class ContentDepth {
    Root, All;

    override fun toString() = when (this) {
        Root -> "root"
        All -> "all"
    }
}
