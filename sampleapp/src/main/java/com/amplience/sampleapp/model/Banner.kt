package com.amplience.sampleapp.model

data class Banner(
    val background: BackgroundImage,
    val headline: String,
    val strapline: String,
    val link: Link
) {
    data class BackgroundImage(val image: Image)
}
