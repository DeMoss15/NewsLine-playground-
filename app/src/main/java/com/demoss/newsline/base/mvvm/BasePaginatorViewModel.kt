package com.demoss.newsline.base.mvvm

import com.demoss.newsline.util.pagination.ReactivePaginator
import com.demoss.newsline.util.pagination.ReactivePaginatorViewState
import io.reactivex.Observable

abstract class BasePaginatorViewModel<ItemType>(requestFabric: (Observable<Int>) -> Observable<List<ItemType>>) :
    BaseViewModel<BasePaginatorUserCommand, ReactivePaginatorViewState>() {

    protected val stateDispatcher: (Observable<ReactivePaginatorViewState>) -> Unit = {
        compositeDisposable.add(it.subscribe { nextState -> _states.value = nextState })
    }
    protected val paginator: ReactivePaginator<ItemType> = ReactivePaginator(requestFabric, stateDispatcher)

    override fun subscribeToUserCommands(commands: Observable<BasePaginatorUserCommand>) {
        super.subscribeToUserCommands(commands)
        commands.doOnDispose { compositeDisposable.clear() }.subscribe()
    }

    override fun dispatchUserCommand(command: BasePaginatorUserCommand): Unit = when (command) {
        RESTART -> paginator.restart()
        REFRESH -> paginator.refresh()
        LOAD_NEXT_PAGE -> paginator.loadNewPage()
    }

    override fun onCleared() {
        paginator.release()
        super.onCleared()
    }
}