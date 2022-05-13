package com.amplience.ampliencesdk.api.models.images

data class Crop(
    val x: Int, // offset from the top left of the image
    val y: Int, // offset from the top of the image
    val w: Int, // width of the selection
    val h: Int // height of the selection
)
