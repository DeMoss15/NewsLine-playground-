package com.demoss.newsline.domain.model

data class Article(
    val source: Source,
    val author: String,
    val tile: String,
    val description: String,
    val url: String,
    val image: String,
    val publishedAt: String
)