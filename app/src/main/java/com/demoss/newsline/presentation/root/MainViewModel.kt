package com.demoss.newsline.presentation.root

import androidx.lifecycle.LiveData
import com.demoss.newsline.base.mvvm.BaseViewModel

class MainViewModel: BaseViewModel<MainAction, MainState>() {

    override val states: LiveData<MainState>
        get() = super.states.also { onFirstViewAttach() }

    private fun onFirstViewAttach() {
        _states.value = InitialState
    }

    override fun executeAction(action: MainAction) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}