package com.demoss.newsline.util.pagination

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.lang.RuntimeException

class ReactivePaginator<T>(
        requestFabric: (Observable<Int>) -> Observable<List<T>>,
        dispatchState: (Observable<ReactivePaginatorViewState>) -> Unit
) {

    companion object {
        const val FIRST_PAGE = 0
    }

    private val disposable: CompositeDisposable = CompositeDisposable()
    private val pageSubject: PublishSubject<Int> = PublishSubject.create()
    private val viewStateSubject: PublishSubject<ReactivePaginatorViewState> = PublishSubject.create()
    private val currentData: MutableList<T> = mutableListOf()
    private var currentState: LoaderState<T> = LOADER_EMPTY()
        set(value) {
            when (value) {
                is LOADER_EMPTY -> viewStateSubject.onNext(EMPTY)
                is LOADER_EMPTY_DATA -> viewStateSubject.onNext(EMPTY_DATA)
                is LOADER_EMPTY_ERROR -> viewStateSubject.onNext(EMPTY_ERROR)
                is LOADER_EMPTY_PROGRESS -> viewStateSubject.onNext(EMPTY_PROGRESS)
                is LOADER_DATA -> viewStateSubject.onNext(DATA(currentData))
                is LOADER_ALL_DATA -> viewStateSubject.onNext(LAST_PAGE(currentData))
                is LOADER_PAGE_PROGRESS -> viewStateSubject.onNext(PAGE_PROGRESS)
                is LOADER_REFRESH -> viewStateSubject.onNext(REFRESH)
                is LOADER_RELEASED -> viewStateSubject.onNext(RELEASED)
                else -> throw RuntimeException("undefined loader class")
            }
            field = value
        }
    private var currentPage: Int = 0

    init {
        disposable.add(requestFabric(pageSubject).subscribe(
                { currentState.newData(it) },
                { currentState.fail(it) }
        ))
        dispatchState(viewStateSubject)
    }

    fun restart() {
        currentState.restart()
    }

    fun refresh() {
        currentState.refresh()
    }

    fun loadNewPage() {
        currentState.loadNewPage()
    }

    fun release() {
        currentState.release()
    }

    private interface LoaderState<T> {
        fun restart() {}
        fun refresh() {}
        fun loadNewPage() {}
        fun release() {}
        fun newData(data: List<T>) {}
        fun fail(error: Throwable) {}
    }

    private inner class LOADER_EMPTY : LoaderState<T> {

        override fun refresh() {
            currentState = LOADER_EMPTY_PROGRESS()
            pageSubject.onNext(FIRST_PAGE)
        }

        override fun release() {
            currentState = LOADER_RELEASED()
        }
    }

    private inner class LOADER_EMPTY_PROGRESS : LoaderState<T> {

        override fun restart() {
            pageSubject.onNext(FIRST_PAGE)
        }

        override fun newData(data: List<T>) {
            currentData.apply {
                clear()
                addAll(data)
            }
            currentPage = FIRST_PAGE

            currentState = if (data.isNotEmpty()) LOADER_DATA()
            else LOADER_EMPTY_DATA()
        }

        override fun fail(error: Throwable) {
            currentState = LOADER_EMPTY_ERROR()
        }

        override fun release() {
            currentState = LOADER_RELEASED()
        }
    }

    private inner class LOADER_EMPTY_ERROR : LoaderState<T> {

        override fun restart() {
            currentState = LOADER_EMPTY_PROGRESS()
            pageSubject.onNext(FIRST_PAGE)
        }

        override fun refresh() {
            currentState = LOADER_EMPTY_PROGRESS()
            pageSubject.onNext(FIRST_PAGE)
        }

        override fun release() {
            currentState = LOADER_RELEASED()
        }
    }

    private inner class LOADER_EMPTY_DATA : LoaderState<T> {

        override fun restart() {
            currentState = LOADER_EMPTY_PROGRESS()
            pageSubject.onNext(FIRST_PAGE)
        }

        override fun refresh() {
            currentState = LOADER_EMPTY_PROGRESS()
            pageSubject.onNext(FIRST_PAGE)
        }

        override fun release() {
            currentState = LOADER_RELEASED()
        }
    }

    private inner class LOADER_DATA : LoaderState<T> {

        override fun restart() {
            currentState = LOADER_EMPTY_PROGRESS()
            pageSubject.onNext(FIRST_PAGE)
        }

        override fun refresh() {
            currentState = LOADER_REFRESH()
            pageSubject.onNext(FIRST_PAGE)
        }

        override fun loadNewPage() {
            currentState = LOADER_PAGE_PROGRESS()
            pageSubject.onNext(++currentPage)
        }

        override fun release() {
            currentState = LOADER_RELEASED()
        }
    }

    private inner class LOADER_REFRESH : LoaderState<T> {

        override fun restart() {
            currentState = LOADER_EMPTY_PROGRESS()
            pageSubject.onNext(FIRST_PAGE)
        }

        override fun newData(data: List<T>) {
            if (data.isNotEmpty()) {
                currentData.apply {
                    clear()
                    addAll(data)
                }
                currentPage = FIRST_PAGE
                currentState = LOADER_DATA()
            } else {
                currentData.clear()
                currentState = LOADER_EMPTY_DATA()
            }
        }

        override fun fail(error: Throwable) {
            currentState = LOADER_DATA()
        }

        override fun release() {
            currentState = LOADER_RELEASED()
        }
    }

    private inner class LOADER_PAGE_PROGRESS : LoaderState<T> {

        override fun restart() {
            currentState = LOADER_EMPTY_PROGRESS()
            pageSubject.onNext(FIRST_PAGE)
        }

        override fun newData(data: List<T>) {
            currentData.addAll(data)
            currentState = if (data.isNotEmpty()) LOADER_DATA()
            else LOADER_ALL_DATA()
        }

        override fun refresh() {
            currentState = LOADER_REFRESH()
            pageSubject.onNext(FIRST_PAGE)
        }

        override fun fail(error: Throwable) {
            currentState = LOADER_DATA()
        }

        override fun release() {
            currentState = LOADER_RELEASED()
        }
    }

    private inner class LOADER_ALL_DATA : LoaderState<T> {

        override fun restart() {
            currentState = LOADER_EMPTY_PROGRESS()
            pageSubject.onNext(FIRST_PAGE)
        }

        override fun refresh() {
            currentState = LOADER_REFRESH()
            pageSubject.onNext(FIRST_PAGE)
        }

        override fun release() {
            currentState = LOADER_RELEASED()
        }
    }

    private inner class LOADER_RELEASED : LoaderState<T> {
        init {
            disposable.dispose()
        }
    }
}
