package com.demoss.translatorflashcards.base.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<UserCommand, State> : ViewModel() {

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()
    // for view state changing
    protected val _states: MutableLiveData<State> = MutableLiveData()
    // accessible for view data
    val states: LiveData<State> get() = _states

    abstract fun subscribeToUserCommands(commands: Observable<UserCommand>)
}