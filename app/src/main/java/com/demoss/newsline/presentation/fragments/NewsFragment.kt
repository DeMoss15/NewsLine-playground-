package com.demoss.newsline.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demoss.newsline.R
import com.demoss.newsline.base.BaseFragment
import com.demoss.newsline.base.mvvm.PAGINATOR_LOAD_NEXT_PAGE
import com.demoss.newsline.base.mvvm.PaginatorAction
import com.demoss.newsline.domain.model.Article
import com.demoss.newsline.util.pagination.*
import kotlinx.android.synthetic.main.fragment_news_data.*
import org.koin.android.viewmodel.ext.android.viewModel

class NewsFragment : BaseFragment<PaginatorAction, PaginatorViewState<Article>, NewsViewModel>() {

    companion object {
        const val TAG = "com.demoss.newsline.presentation.fragments.NewsFragment"
    }

    override val layoutResourceId: Int = R.layout.fragment_news_empty_progress
    override val viewModel by viewModel<NewsViewModel>()

    private val rvAdapter: ArticlesRecyclerViewAdapter = ArticlesRecyclerViewAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragment_news_constraint.loadLayoutDescription(R.xml.fragment_news)

        rvArticles.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            setOnNextPageListener(RecyclerView.FOCUS_DOWN) {
                viewModel.executeAction(PAGINATOR_LOAD_NEXT_PAGE)
            }
        }
    }

    override fun dispatchState(newStatus: PaginatorViewState<Article>) {
        setConstraintState(when (newStatus) {
            is EMPTY -> setMessageState(R.string.empty_state)
            is EMPTY_PROGRESS -> R.id.empty_progress
            is EMPTY_ERROR -> setMessageState(R.string.empty_error)
            is EMPTY_DATA -> setMessageState(R.string.empty_data)
            is PAGE_PROGRESS -> R.id.data_progress
            is REFRESH -> R.id.data_progress
            is RELEASED -> setMessageState(R.string.released)
            is DATA -> showDataState(newStatus.data)
            is LAST_PAGE -> showDataState(newStatus.data)
        })
    }

    private fun showDataState(data: List<Article>): Int = R.id.data.also { rvAdapter.dispatchData(data) }

    private fun setMessageState(@StringRes message: Int): Int = R.id.message.also { tvMessage.text = getString(message) }

    private fun setConstraintState(@IdRes id: Int) {
        fragment_news_constraint.setState(id, 0, 0)
    }
}