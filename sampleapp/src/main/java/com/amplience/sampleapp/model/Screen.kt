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

    class MultiContentExampleScreen(
        override val name: String = "Multi content example",
        override val id: String = "multi-content",
        val banner: Banner?,
        val slides: List<ImageSlide>?,
        val text: String?,
        val video: Video?
    ) : Screen()

    class BlogPostMenuScreen(
        override val name: String = "Blog menu",
        override val id: String = "blog-menu",
        val posts: List<BlogPostScreen>
    ) : Screen()

    class BlogPostScreen(
        val blogPost: BlogPost,
        override val name: String = "Blog",
        override val id: String = "blog-${blogPost.title}"
    ) : Screen()
}
