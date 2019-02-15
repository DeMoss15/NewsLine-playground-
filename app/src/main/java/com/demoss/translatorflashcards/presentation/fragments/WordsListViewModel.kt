package com.demoss.translatorflashcards.presentation.fragments

import androidx.lifecycle.MutableLiveData
import com.demoss.translatorflashcards.base.mvvm.BaseViewModel
import com.demoss.translatorflashcards.domain.model.Translation
import com.demoss.translatorflashcards.util.pagination.*
import io.reactivex.Observable

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

    override fun subscribeToUserCommands(commands: Observable<WordsListCommand>) {
        compositeDisposable.add(commands.subscribe {
            when (it) {
                NEXT_STATE -> _listStateLiveData.value = listStates[++currentListStateNumber % listStates.size]
            }
        })
    }
}