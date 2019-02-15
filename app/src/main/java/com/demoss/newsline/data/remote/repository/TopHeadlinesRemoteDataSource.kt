package com.demoss.newsline.data.remote.repository

import com.demoss.newsline.data.remote.RemoteDomainMapper
import com.demoss.newsline.data.remote.api.NewsApi
import com.demoss.newsline.data.remote.api.requests.NewsApiTopNewsRequestBuilder
import com.demoss.newsline.domain.model.Article
import io.reactivex.Observable
import java.lang.RuntimeException

class TopHeadlinesRemoteDataSource(val newsApi: NewsApi) : TopHeadlinesRemoteRepository {

    override fun getTopHeadlines(
        page: Int,
        query: String?,
        sources: String?,
        category: String?,
        country: String?
    ): Observable<List<Article>> = newsApi.getTopNews(NewsApiTopNewsRequestBuilder().apply {
        this.page = page
        this.query = query
        this.sources = sources
        this.category = category
        this.country = country
    }.build()).map {
        if (it.code() != 200 || it.body()?.articles == null) throw RuntimeException(it.message())
        it.body()?.articles?.let { articles -> RemoteDomainMapper.toDomain(articles) }
    }
}