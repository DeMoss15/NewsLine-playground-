package com.demoss.newsline.domain.model

import kotlinx.coroutines.channels.Channel

data class Article(
        val source: Source,
        val author: String,
        val title: String,
        val description: String,
        val url: String,
        val image: String,
        val publishedAt: String
) {
    var channelProgress: Channel<Int> = Channel()

    override fun equals(other: Any?): Boolean = other is Article &&
            other.source == source &&
            other.author == author &&
            other.title == title &&
            other.description == description &&
            other.url == url &&
            other.image == image &&
            other.publishedAt == publishedAt

    override fun hashCode(): Int = source.hashCode() * 31 +
            author.hashCode() + 31 +
            title.hashCode() * 31 +
            description.hashCode() * 31 +
            url.hashCode() * 31 +
            image.hashCode() * 31 +
            publishedAt.hashCode() * 31
}