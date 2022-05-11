package com.amplience.ampliencesdk.models

sealed class Element {
    data class TextElement(
        val text: String
    ) : Element() {
        companion object {
            fun getFromMap(map: Map<String, Any>) = TextElement(map["text"].toString())
        }
    }
}
