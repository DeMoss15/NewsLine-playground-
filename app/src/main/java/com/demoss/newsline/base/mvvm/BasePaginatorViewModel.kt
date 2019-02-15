package com.demoss.newsline.base.mvvm

import com.demoss.newsline.util.pagination.ReactivePaginator
import com.demoss.newsline.util.pagination.ReactivePaginatorViewState
import io.reactivex.Observable

abstract class BasePaginatorViewModel<ItemType> : BaseViewModel<BasePaginatorUserCommand, ReactivePaginatorViewState>() {

    abstract val requestFabric: (Observable<Int>) -> Observable<List<ItemType>>

    protected val stateDispatcher: (Observable<ReactivePaginatorViewState>) -> Unit = {
        compositeDisposable.add(it.subscribe { nextState -> _states.value = nextState })
    }
    protected lateinit var paginator: ReactivePaginator<ItemType>

    override fun subscribeToUserCommands(commands: Observable<BasePaginatorUserCommand>) {
        super.subscribeToUserCommands(commands)
        paginator = ReactivePaginator(requestFabric, stateDispatcher)
        commands.doOnDispose { compositeDisposable.clear() }.subscribe()
        paginator.refresh()
    }

    override fun dispatchUserCommand(command: BasePaginatorUserCommand): Unit = when (command) {
        PAGINATOR_RESTART -> paginator.restart()
        PAGINATOR_REFRESH -> paginator.refresh()
        PAGINATOR_LOAD_NEXT_PAGE -> paginator.loadNewPage()
    }

    override fun onCleared() {
        paginator.release()
        super.onCleared()
    }
}