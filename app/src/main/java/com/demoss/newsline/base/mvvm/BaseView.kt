package com.demoss.newsline.base.mvvm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import io.reactivex.subjects.PublishSubject

interface BaseView<UserCommand, State, VM : BaseViewModel<UserCommand, State>> {

    // for sending commands to view model
    val userCommands: PublishSubject<UserCommand>
    var viewModel: VM

    fun dispatchState(newStatus: State)
    fun subscribeToViewModel(lifecycleOwner: LifecycleOwner) {
        viewModel.subscribeToUserCommands(userCommands)
        viewModel.states.observe(lifecycleOwner, Observer<State> { newState -> dispatchState(newState) })
    }
}