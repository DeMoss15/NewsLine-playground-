package com.demoss.newsline.data.remote.repository

import com.demoss.newsline.domain.model.Article

interface TopHeadlinesRemoteRepository {
    suspend fun getTopHeadlines(
        page: Int,
        query: String? = null,
        sources: String? = null,
        category: String? = null,
        country: String? = null
    ): List<Article>
}