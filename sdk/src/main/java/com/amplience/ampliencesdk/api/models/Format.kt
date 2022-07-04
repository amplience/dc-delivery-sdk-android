package com.amplience.ampliencesdk.api.models

enum class Format {
    Inlined, Linked;

    override fun toString() = when (this) {
        Inlined -> "inlined"
        Linked -> "linked"
    }
}