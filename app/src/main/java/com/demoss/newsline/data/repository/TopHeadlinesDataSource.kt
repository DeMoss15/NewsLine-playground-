package com.demoss.newsline.data.repository

import com.demoss.newsline.data.remote.repository.TopHeadlinesRemoteRepository
import com.demoss.newsline.domain.model.Article
import io.reactivex.Observable

class TopHeadlinesDataSource(val remoteRepository: TopHeadlinesRemoteRepository) : TopHeadlinesRepository {

    override fun getTopHeadlines(
        page: Int,
        query: String?,
        sources: String?,
        category: String?,
        country: String?
    ): Observable<List<Article>> =
        remoteRepository.getTopHeadlines(page, query, sources, category, country)
}