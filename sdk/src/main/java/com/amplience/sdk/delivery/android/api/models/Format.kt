package com.amplience.sdk.delivery.android.api.models

enum class Format {
    Inlined, Linked;

    override fun toString() = when (this) {
        Inlined -> "inlined"
        Linked -> "linked"
    }
}
