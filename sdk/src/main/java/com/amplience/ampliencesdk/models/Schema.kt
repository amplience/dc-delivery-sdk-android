package com.amplience.ampliencesdk.models

import com.google.gson.annotations.SerializedName

data class Schema(
    val title: String,
    val description: String,
    val propertyOrder: List<String>,
    val type: String,
    val properties: Map<String, Any>
) {
    data class Property(
        val name: PropertyName?,
        val title: String,
        val description: String,
        val type: Type
    ) {
        enum class Type {
            @SerializedName("string")
            String, // A string of text that may contain unicode characters

            @SerializedName("number")
            Number, // Any numeric type. It can be an integer or floating point number

            @SerializedName("integer")
            Integer, //	An integral number

            @SerializedName("boolean")
            Boolean, // A value that can be true or false

            @SerializedName("object")
            Object, // A JSON object. Can be defined inline or via a $ref to an internal or external type

            @SerializedName("array")
            Array // A JSON array containing ordered elements. Typically used in nested content types to stored linked content items. See the Carousel sample for an example of its use.
        }
    }

    fun getProperty(propertyName: String): Property? {
        val subMap = properties[propertyName] as? Map<String, Any>
        return if (subMap == null) {
            null
        } else {
            Property(
                name = PropertyName.parse(propertyName),
                title = subMap["title"].toString(),
                description = subMap["description"].toString(),
                type = Property.Type.valueOf(subMap["type"].toString())
            )
        }
    }
}
