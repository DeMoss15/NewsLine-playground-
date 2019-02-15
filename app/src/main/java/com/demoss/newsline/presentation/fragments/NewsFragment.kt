package com.demoss.newsline.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demoss.newsline.R
import com.demoss.newsline.base.BaseFragment
import com.demoss.newsline.base.mvvm.BasePaginatorUserCommand
import com.demoss.newsline.base.mvvm.PAGINATOR_LOAD_NEXT_PAGE
import com.demoss.newsline.base.mvvm.PAGINATOR_REFRESH
import com.demoss.newsline.domain.model.Article
import com.demoss.newsline.util.pagination.*
import kotlinx.android.synthetic.main.fragment_news_data.*
import org.koin.android.ext.android.inject

class NewsFragment : BaseFragment<BasePaginatorUserCommand, ReactivePaginatorViewState, NewsViewModel>() {

    override val layoutResourceId: Int = R.layout.fragment_news_empty_progress

    private val vmFactory: NewsModelFactory by inject()
    override lateinit var viewModel: NewsViewModel

    private val rvAdapter: ArticlesRecyclerViewAdapter = ArticlesRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, vmFactory).get(NewsViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragment_news_constraint.loadLayoutDescription(R.xml.fragment_news)
        rvArticles.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            setOnNextPageListener(RecyclerView.FOCUS_DOWN) {
                userCommands.onNext(PAGINATOR_LOAD_NEXT_PAGE)
            }
        }
    }

    override fun dispatchState(newStatus: ReactivePaginatorViewState) {
        setConstraintState(when (newStatus) {
            EMPTY -> setMessageState(R.string.empty_state)
            EMPTY_PROGRESS -> R.id.empty_progress
            EMPTY_ERROR -> setMessageState(R.string.empty_error)
            EMPTY_DATA -> setMessageState(R.string.empty_data)
            PAGE_PROGRESS -> R.id.data_progress
            REFRESH -> R.id.data_progress
            RELEASED -> setMessageState(R.string.released)
            is DATA<*> -> showDataState(newStatus.data as List<Article>)
            is LAST_PAGE<*> -> showDataState(newStatus.data as List<Article>)
        })
    }

    private fun showDataState(data: List<Article>): Int {
        rvAdapter.dispatchData(data)
        return R.id.data
    }

    private fun setMessageState(@StringRes message: Int): Int {
        tvMessage.text = getString(message)
        return R.id.message
    }

    private fun setConstraintState(@IdRes id: Int) {
        fragment_news_constraint.setState(id, 0, 0)
    }
}