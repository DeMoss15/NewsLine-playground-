package com.demoss.newsline.domain.usecase

import com.demoss.newsline.data.repository.TopHeadlinesRepository
import com.demoss.newsline.domain.model.Article
import com.demoss.newsline.domain.usecase.base.RxUseCaseObservable
import io.reactivex.Observable

class GetTopHeadlinesUseCase(private val topHeadlinesRepository: TopHeadlinesRepository) :
    RxUseCaseObservable<List<Article>, GetTopHeadlinesUseCase.Params>() {

    override fun buildUseCaseObservable(params: Params): Observable<List<Article>> = params.let {
        topHeadlinesRepository.getTopHeadlines(it.page, it.query, it.sources, it.category, it.country)
    }

    class Params(
        val page: Int,
        val query: String,
        val sources: String,
        val category: String,
        val country: String
    )
}