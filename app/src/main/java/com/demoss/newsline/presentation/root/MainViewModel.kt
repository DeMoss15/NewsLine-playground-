package com.demoss.newsline.presentation.root

import com.demoss.newsline.base.mvvm.BaseViewModel
import io.reactivex.Observable

class MainViewModel: BaseViewModel<MainCommand, MainState>() {

    override fun subscribeToUserCommands(commands: Observable<MainCommand>) {
        super.subscribeToUserCommands(commands)
        _states.value = InitialState
    }

    override fun dispatchUserCommand(command: MainCommand) {
        // TODO: Dispatch command
    }
}