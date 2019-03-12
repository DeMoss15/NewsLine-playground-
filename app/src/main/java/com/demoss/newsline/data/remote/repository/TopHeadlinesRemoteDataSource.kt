package com.demoss.newsline.data.remote.repository

import com.demoss.newsline.data.remote.RemoteDomainMapper
import com.demoss.newsline.data.remote.api.NewsApi
import com.demoss.newsline.data.remote.api.requests.NewsApiTopNewsRequestBuilder
import com.demoss.newsline.domain.model.Article

class TopHeadlinesRemoteDataSource(private val newsApi: NewsApi) : TopHeadlinesRemoteRepository {

    override suspend fun getTopHeadlines(
        page: Int,
        query: String?,
        sources: String?,
        category: String?,
        country: String?
    ): List<Article> =  newsApi.getTopNewsAsync(NewsApiTopNewsRequestBuilder().apply {
            this.page = page
            this.query = query
            this.sources = sources
            this.category = category
            this.country = country
        }.build()).await().run {
        if (code() != 200 || body()?.articles == null) throw RuntimeException(message())
        body()?.articles?.let { articles -> RemoteDomainMapper.toDomain(articles) } ?: listOf()
    }
}