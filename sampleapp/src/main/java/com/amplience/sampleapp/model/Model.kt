package com.amplience.sampleapp.model

data class Slide(
    val imageItem: ImageItem
)

data class ImageItem(
    val image: Image
)

data class Image(
    val name: String
)
