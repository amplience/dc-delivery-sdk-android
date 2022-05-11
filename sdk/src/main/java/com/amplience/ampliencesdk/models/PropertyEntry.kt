package com.amplience.ampliencesdk.models

data class PropertyEntry(
    val value: Any?,
    val property: Schema.Property
) {
    val stringValue = value.toString()
}
