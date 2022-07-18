package com.amplience.ampliencesdk.api.models.images

enum class ColorSpace {
    RGB, RGBA, SRGB, Gray, CMYK, OHTA, LAB, XYZ, HSB, HSL;

    override fun toString(): String = when (this) {
        RGB -> "rgb"
        RGBA -> "rgba"
        SRGB -> "srgb"
        Gray -> "gray"
        CMYK -> "cmyk"
        OHTA -> "ohta"
        LAB -> "lab"
        XYZ -> "xyz"
        HSB -> "hsb"
        HSL -> "hsl"
    }
}
