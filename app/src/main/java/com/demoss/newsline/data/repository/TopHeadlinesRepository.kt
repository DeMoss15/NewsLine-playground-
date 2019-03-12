package com.demoss.newsline.data.repository

import com.demoss.newsline.domain.model.Article

interface TopHeadlinesRepository {
    suspend fun getTopHeadlines(
        page: Int,
        query: String? = null,
        sources: String? = null,
        category: String? = null,
        country: String? = null
    ): List<Article>
}