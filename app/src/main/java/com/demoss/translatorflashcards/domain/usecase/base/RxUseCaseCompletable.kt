package com.demoss.translatorflashcards.domain.usecase.base

import com.demoss.translatorflashcards.util.setDefaultSchedulers
import io.reactivex.Completable
import io.reactivex.observers.DisposableCompletableObserver

abstract class RxUseCaseCompletable<Params> : BaseRxUseCase<Params, Completable, DisposableCompletableObserver>() {
    final override fun execute(observer: DisposableCompletableObserver, params: Params) {
        addDisposable(
            buildUseCaseObservable(params)
                .setDefaultSchedulers()
                .subscribeWith(observer)
        )
    }
}