package com.demoss.translatorflashcards.domain.usecase.base

import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver

abstract class RxUseCaseObservable<T, Params> : BaseRxUseCase<Params, Observable<T>, DisposableObserver<T>>() {
    final override fun execute(observer: DisposableObserver<T>, params: Params) {
        addDisposable(
            buildUseCaseObservable(params)
                .setDefaultSchedulers()
                .subscribeWith(observer)
        )
    }
}