package com.demoss.newsline.data.remote.repository

import com.demoss.newsline.domain.model.Article
import io.reactivex.Observable

interface TopHeadlinesRemoteRepository {
    fun getTopHeadlines(
        page: Int,
        query: String? = null,
        sources: String? = null,
        category: String? = null,
        country: String? = null
    ): Observable<List<Article>>
}