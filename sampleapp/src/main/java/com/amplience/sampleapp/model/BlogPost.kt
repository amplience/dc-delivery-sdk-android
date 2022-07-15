package com.amplience.sampleapp.model

data class BlogPost(
    val title: String,
    val category: String,
    val date: String,
    val ranking: Int,
    val description: String,
    val readTime: Int,
    val authors: List<Author>,
    val image: BlogImage,
    val content: List<BlogContent>
)

data class BlogContent(
    val text: String
)

data class Author(
    val name: String,
    val avatar: BlogImage
)

data class BlogImage(
    val image: Image,
    val altText: String
)
