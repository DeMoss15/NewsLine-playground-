package com.demoss.newsline.presentation.fragments

import androidx.lifecycle.viewModelScope
import com.demoss.newsline.base.mvvm.BaseViewModel
import com.demoss.newsline.domain.model.Article
import com.demoss.newsline.domain.usecase.GetTopHeadlinesUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsViewModel(private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase) : BaseViewModel<NewsAction, NewsState>() {

    override fun executeAction(action: NewsAction) {
        when(action) {
            is LoadNews -> viewModelScope.launch {
                _states.value = NewsData(getTopHeadlinesUseCase.execute(getParams(1)))
            }
        }
    }

    fun loadItem(item: Article) {
        viewModelScope.launch {
            var progress = 0
            while (progress != 100) {
                delay(100)
                item.channelProgress.send(progress++)
            }
        }
    }

    private var params: GetTopHeadlinesUseCase.Params = GetTopHeadlinesUseCase.Params(0)
    private fun getParams(page: Int): GetTopHeadlinesUseCase.Params = params.apply { this.page = page }
}

sealed class NewsAction
object LoadNews: NewsAction()

sealed class NewsState
class NewsData(val data: List<Article>): NewsState()