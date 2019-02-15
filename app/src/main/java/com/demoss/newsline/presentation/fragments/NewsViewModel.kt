package com.demoss.newsline.presentation.fragments

import com.demoss.newsline.base.mvvm.BasePaginatorViewModel
import com.demoss.newsline.domain.model.Article
import com.demoss.newsline.domain.usecase.GetTopHeadlinesUseCase
import io.reactivex.Observable

class NewsViewModel(private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase): BasePaginatorViewModel<Article>()  {

    override val requestFabric: (Observable<Int>) -> Observable<List<Article>> = {
        it.flatMap { newPage -> getTopHeadlinesUseCase.buildUseCaseObservable(getParams(newPage)) }
    }

    private var params: GetTopHeadlinesUseCase.Params = GetTopHeadlinesUseCase.Params(0)
    private fun getParams(page: Int): GetTopHeadlinesUseCase.Params  = params.apply { this.page = page }
}