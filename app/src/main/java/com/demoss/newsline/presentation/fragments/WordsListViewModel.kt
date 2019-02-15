package com.demoss.newsline.presentation.fragments

import androidx.lifecycle.MutableLiveData
import com.demoss.newsline.base.mvvm.BaseViewModel
import com.demoss.newsline.util.pagination.*

class WordsListViewModel : BaseViewModel<WordsListCommand, WordsListState>() {

    private val _listStateLiveData: MutableLiveData<ReactivePaginatorViewState> = MutableLiveData()
    val listStateLiveData: MutableLiveData<ReactivePaginatorViewState>
        get() = _listStateLiveData

    private val listStates = listOf(
            EMPTY,
            EMPTY_PROGRESS,
            EMPTY_ERROR,
            EMPTY_DATA,
            PAGE_PROGRESS,
            REFRESH,
            RELEASED,
            DATA<Translation>(listOf()),
            LAST_PAGE<Translation>(listOf())
    )

    private var currentListStateNumber = -1

    override fun dispatchUserCommand(command: WordsListCommand) {
        when (command) {
            NEXT_STATE -> _listStateLiveData.value = listStates[++currentListStateNumber % listStates.size]
        }
    }
}