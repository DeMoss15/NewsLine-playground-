package com.demoss.newsline.base.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.demoss.newsline.util.pagination.Paginator
import com.demoss.newsline.util.pagination.PaginatorViewState

abstract class BasePaginatorViewModel<ItemType> : BaseViewModel<PaginatorAction, PaginatorViewState<ItemType>>() {

    protected abstract val requestFabric: suspend (Int) -> List<ItemType>

    protected val paginator: Paginator<ItemType> by lazy { Paginator(viewModelScope, requestFabric) }

    override val states: LiveData<PaginatorViewState<ItemType>>
        get() = paginator.viewStatesLiveData

    override fun executeAction(action: PaginatorAction): Unit = when (action) {
        PAGINATOR_RESTART -> paginator.restart()
        PAGINATOR_REFRESH -> paginator.refresh()
        PAGINATOR_LOAD_NEXT_PAGE -> paginator.loadNewPage()
    }

    override fun onCleared() {
        paginator.release()
        super.onCleared()
    }
}