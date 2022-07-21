package com.amplience.ampliencesdk.api.models

enum class ContentDepth {
    Root, All;

    override fun toString() = when (this) {
        Root -> "root"
        All -> "all"
    }
}
