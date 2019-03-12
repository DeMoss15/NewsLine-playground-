package com.demoss.newsline.data.repository

import com.demoss.newsline.data.remote.repository.TopHeadlinesRemoteRepository
import com.demoss.newsline.domain.model.Article

class TopHeadlinesDataSource(val remoteRepository: TopHeadlinesRemoteRepository) : TopHeadlinesRepository {

    override suspend fun getTopHeadlines(
        page: Int,
        query: String?,
        sources: String?,
        category: String?,
        country: String?
    ): List<Article> =
        remoteRepository.getTopHeadlines(page, query, sources, category, country)
}