package com.amplience.ampliencesdk.models

enum class PropertyName(val apiName: String) {
    Headline("headline"),
    Strapline("strapline"),
    CTAText("calltoactiontext"),
    CTAUrl("calltoactionurl");

    companion object {
        fun parse(string: String) = values().find { it.apiName == string }
    }

    override fun toString(): String = apiName
}