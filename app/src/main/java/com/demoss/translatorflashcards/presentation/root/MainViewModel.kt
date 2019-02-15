package com.demoss.translatorflashcards.presentation.root

import com.demoss.translatorflashcards.base.mvvm.BaseViewModel
import io.reactivex.Observable

class MainViewModel: BaseViewModel<MainCommand, MainState>() {

    override fun subscribeToUserCommands(commands: Observable<MainCommand>) {
        compositeDisposable.add(commands.subscribe {
            /*TODO: add command execution*/
        })
        _states.value = InitialState
    }
}