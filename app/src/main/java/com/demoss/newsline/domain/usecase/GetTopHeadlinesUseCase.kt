package com.demoss.newsline.domain.usecase

import com.demoss.newsline.data.repository.TopHeadlinesRepository
import com.demoss.newsline.domain.model.Article
import com.demoss.newsline.domain.usecase.base.BaseUseCase

class GetTopHeadlinesUseCase(private val topHeadlinesRepository: TopHeadlinesRepository) :
    BaseUseCase<List<Article>, GetTopHeadlinesUseCase.Params> {

    override suspend fun execute(params: Params): List<Article> = params.let {
        topHeadlinesRepository.getTopHeadlines(it.page, it.query, it.sources, it.category, it.country)
    }

    class Params(
        var page: Int,
        var query: String? = null,
        var sources: String? = null,
        var category: String? = null,
        var country: String? = null
    )
}