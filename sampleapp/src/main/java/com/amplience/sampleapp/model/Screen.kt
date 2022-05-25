package com.amplience.sampleapp.model

sealed class Screen {
    abstract val name: String
    abstract val id: String

    class HomeScreen(
        override val name: String = "Home",
        override val id: String = "home"
    ) : Screen()

    class BannerScreen(
        val banner: Banner,
        override val name: String = "Banner",
        override val id: String = "banner"
    ) : Screen()

    class SlidesScreen(
        val slides: List<Slide>,
        override val name: String = "Slides",
        override val id: String = "slides"
    ) : Screen()
}
