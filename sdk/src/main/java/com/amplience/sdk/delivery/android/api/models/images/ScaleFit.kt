package com.amplience.sdk.delivery.android.api.models.images

sealed class ScaleFit {
    object Center : ScaleFit() // Default
    class Poi(val x: Double, val y: Double, val w: Double, val h: Double) : ScaleFit()

    override fun toString(): String = when (this) {
        Center -> "center"
        is Poi -> "poi&poi=$x,$y,$w,$h"
    }
}
