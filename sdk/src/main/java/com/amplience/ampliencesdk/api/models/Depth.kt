package com.amplience.ampliencesdk.api.models

enum class Depth {
    Root, All;

    override fun toString() = when (this) {
        Root -> "root"
        All -> "all"
    }
}
