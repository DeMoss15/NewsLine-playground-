package com.demoss.translatorflashcards.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.demoss.translatorflashcards.R
import com.demoss.translatorflashcards.base.BaseFragment
import com.demoss.translatorflashcards.util.pagination.*
import kotlinx.android.synthetic.main.fragment_words_list_data.*

class WordsListFragment : BaseFragment<WordsListCommand, WordsListState, WordsListViewModel>() {

    override val layoutResourceId: Int = R.layout.fragment_words_list_empty_state
    override lateinit var viewModel: WordsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(WordsListViewModel::class.java)
        super.onCreate(savedInstanceState)
        viewModel.listStateLiveData.observe(this, Observer<ReactivePaginatorViewState> { newState -> dispatchListState(newState) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userCommands.onNext(NEXT_STATE)
        wordsListConstraint.loadLayoutDescription(R.xml.fragment_words_list)
        fabImportWords.setOnClickListener { userCommands.onNext(NEXT_STATE) }
    }

    override fun dispatchState(newStatus: WordsListState) {
        /*TODO: Dispatch data*/
    }

    private fun dispatchListState(listState: ReactivePaginatorViewState) {
        setConstraintState(when (listState) {
            EMPTY -> R.id.empty_state
            EMPTY_PROGRESS -> R.id.empty_progress
            EMPTY_ERROR -> R.id.empty_error
            EMPTY_DATA -> R.id.empty_data
            PAGE_PROGRESS -> R.id.page_progress
            REFRESH -> R.id.refresh
            RELEASED -> R.id.released
            is DATA<*> -> R.id.data
            is LAST_PAGE<*> -> R.id.last_page
        })
    }

    private fun setConstraintState(@IdRes id: Int) {
        wordsListConstraint.setState(id, 0, 0)
    }
}
